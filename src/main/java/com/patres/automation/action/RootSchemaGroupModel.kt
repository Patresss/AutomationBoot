package com.patres.automation.action

import com.patres.automation.ApplicationLauncher
import com.patres.automation.file.TmpFileLoader
import com.patres.automation.gui.controller.model.RootSchemaGroupController
import com.patres.automation.gui.dialog.LogManager
import com.patres.automation.gui.dialog.SaveRecordedActionsDialog
import com.patres.automation.listener.record.ActionRecorder
import com.patres.automation.settings.LocalSettings
import javafx.application.Platform
import javafx.beans.property.SimpleBooleanProperty
import javafx.concurrent.Task
import org.slf4j.LoggerFactory

class RootSchemaGroupModel(
        val localSettings: LocalSettings = LocalSettings(),
        val rootFiles: RootSchemaFiles
) {

    var automationRunningProperty = SimpleBooleanProperty(false)
    var actionRunner = ActionRunner(automationRunningProperty)

    val actionRecorder = ActionRecorder()
    var loaded: Boolean = false

    val controller: RootSchemaGroupController = RootSchemaGroupController(this)
    val schemaGroupModel
        get() = controller.schemaGroupController.toModel()

    fun runAutomation() {
        actionRunner.runAutomation(schemaGroupModel)
    }

    fun stopAutomation() {
        actionRunner.stopAutomation()
    }

    private fun saveTmpFile() {
        TmpFileLoader.saveTmpFile(this)
    }

    fun changeDetect() {
        if (loaded) {
            saveTmpFile()
            ApplicationLauncher.mainController?.changeDetect(this)
        }
    }

    fun getFilePathToSettings(): String {
        return rootFiles.currentFile.absolutePath
    }

    fun getName() = rootFiles.getName()

    fun getEndpointName() = if (localSettings.endpointName.isNotBlank()) localSettings.endpointName else getName().replace("\\s".toRegex(), "")

    fun startRecord() {
        val startRecordTask = object : Task<Void>() {
            override fun call(): Void? {
                if (!actionRecorder.recordRunningProperty.get()) {
                    Platform.runLater { ApplicationLauncher.mainStage.isIconified = true }
                    actionRecorder.record()
                }
                return null
            }
        }
        Thread(startRecordTask).run()
    }

    fun stopRecord() {
        if (actionRecorder.recordRunningProperty.get()) {
            val recordedActions = actionRecorder.stopRecording()
            SaveRecordedActionsDialog(recordedActions, controller).showDialog()
        }
    }

    fun isSaved() = rootFiles.orgFile == null && !rootFiles.currentFileIsTemp()

    fun hasActions() = controller.schemaGroupController.abstractBlocks.isNotEmpty()
}