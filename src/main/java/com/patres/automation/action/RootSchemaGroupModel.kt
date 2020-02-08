package com.patres.automation.action

import com.patres.automation.ApplicationLauncher
import com.patres.automation.excpetion.ApplicationException
import com.patres.automation.file.TmpFileLoader
import com.patres.automation.gui.controller.model.RootSchemaGroupController
import com.patres.automation.gui.dialog.LogManager
import com.patres.automation.keyboard.listener.RootSchemaKeyListener
import com.patres.automation.settings.GlobalSettingsLoader
import com.patres.automation.settings.LocalSettings
import com.patres.automation.record.ActionRecorder
import javafx.application.Platform
import javafx.beans.property.SimpleBooleanProperty
import javafx.concurrent.Task
import org.slf4j.LoggerFactory
import java.io.File

class RootSchemaGroupModel(
        val localSettings: LocalSettings = LocalSettings(),
        var tmpFile: File = TmpFileLoader.createNewTmpFile(),
        var file: File? = null,
        var saved: Boolean = true
) {

    companion object {
        private val logger = LoggerFactory.getLogger(RootSchemaGroupModel::class.java)
    }

    var automationRunningProperty = SimpleBooleanProperty(false)
    val actionRecorder = ActionRecorder()

    var loaded: Boolean = false

    val rootSchemaKeyListener = RootSchemaKeyListener(this)

    val controller: RootSchemaGroupController = RootSchemaGroupController(this)

    fun runAutomation(hideApplication: Boolean = true) {
        try {
            val schemaGroupModel = controller.schemaGroupController.toModel()
            val runTask = createRunTask(schemaGroupModel, hideApplication)
            Thread(runTask).start()
        } catch (e: Exception) {
            LogManager.showAndLogException(e)
        }
    }

    fun stopAutomation() {
        automationRunningProperty.set(false)
    }

    private fun createRunTask(schemaGroupModel: SchemaGroupModel, hideApplication: Boolean = false): Task<Void> {
        return object : Task<Void>() {
            override fun call(): Void? {
                automationRunningProperty.set(true)
                try {
                    rootSchemaKeyListener.reset()
                    if (hideApplication) {
                        Platform.runLater { ApplicationLauncher.mainStage.isIconified = true }
                    }
                    logger.info("Running root actions...")
                    schemaGroupModel.runAction()
                    logger.info("Completed root actions")
                    Thread.sleep(200)
                } catch (e: ApplicationException) {
                    LogManager.showAndLogException(e)
                } catch (e: Exception) {
                    logger.error("Exception: {}", e)
                } finally {
                    if (hideApplication) {
                        Platform.runLater { ApplicationLauncher.mainStage.isIconified = false }
                    }
                    automationRunningProperty.set(false)
                }
                return null
            }
        }
    }

    fun saveTmpFile() {
        TmpFileLoader.saveTmpFile(this) // TODO refactor -> 1 loader per root - not singleton
    }

    fun changeDetect() {
        if (loaded) {
            if (saved) {
                saved = false
                GlobalSettingsLoader.save()
            }
            saved = false
            saveTmpFile()
            ApplicationLauncher.mainController?.changeDetect(this)
        }
    }

    fun getFilePathToSettings(): String {
        val constFile = file
        return if (saved && constFile != null) {
            constFile.absolutePath
        } else {
            tmpFile.absolutePath
        }
    }

    fun getName() = file?.nameWithoutExtension ?: tmpFile.nameWithoutExtension

    fun getEndpointName() = if (localSettings.endpointName.isNotBlank()) localSettings.endpointName else getName().replace("\\s".toRegex(), "")

    fun startRecord() {
        actionRecorder.record()
    }

    fun stopRecord() {
        val recordedActions = actionRecorder.stopRecording()
        val controllers = recordedActions.map { it.serializedToController() }
        controllers.forEach {controller.addActionBlocks(it)  }
    }

}