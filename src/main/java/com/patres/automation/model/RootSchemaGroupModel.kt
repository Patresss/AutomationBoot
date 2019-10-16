package com.patres.automation.model

import com.patres.automation.Main
import com.patres.automation.excpetion.ApplicationException
import com.patres.automation.file.TmpFileLoader
import com.patres.automation.gui.controller.model.AutomationController
import com.patres.automation.gui.controller.model.RootSchemaGroupController
import com.patres.automation.gui.controller.model.SchemaGroupController
import com.patres.automation.gui.controller.settings.LocalSettingsController
import com.patres.automation.gui.dialog.ExceptionHandlerDialog
import com.patres.automation.keyboard.listener.RootSchemaKeyListener
import com.patres.automation.settings.GlobalSettingsLoader
import com.patres.automation.settings.LocalSettings
import javafx.animation.Interpolator
import javafx.animation.KeyFrame
import javafx.animation.KeyValue
import javafx.animation.Timeline
import javafx.application.Platform
import javafx.beans.property.SimpleBooleanProperty
import javafx.concurrent.Task
import javafx.util.Duration
import org.slf4j.LoggerFactory
import java.awt.Robot
import java.io.File

class RootSchemaGroupModel(
        val localSettings: LocalSettings = LocalSettings(),
        var tmpFile: File = TmpFileLoader.createNewTmpFile(),
        var file: File? = null,
        var saved: Boolean = true
) {

    companion object {
        private val LOGGER = LoggerFactory.getLogger(RootSchemaGroupModel::class.java)
    }

    var automationRunningProperty = SimpleBooleanProperty(false)

    var loaded: Boolean = false

    val controller: RootSchemaGroupController = RootSchemaGroupController(this)

    var robot = Robot()

    val rootSchemaKeyListener = RootSchemaKeyListener(this)

    var schemaGroup: SchemaGroupModel? = null

    var schemaGroupController = SchemaGroupController(root = this, parent = null)
        set(value) {
            field = value
            loadControllerContent()
        }


    var selectedModel: AutomationController<*> = schemaGroupController
        set(value) {
            field = value
            controller.actionBarController.updateDisabledButtons()
        }

    private val allChildrenActionBlocksRoot
        get() = schemaGroupController.allChildrenActionBlocks + schemaGroupController

    init {
        controller.actionBarController.initAfterSetModel()
        loadControllerContent()
    }

    fun unselectAllButton() {
        allChildrenActionBlocksRoot.forEach { it.unselectSelectActionButton() }
    }

    fun removeSelectedModel() {
        val futureSelectedNode = selectedModel.findNodeOnTheTop()
        selectedModel.parent?.removeNode(selectedModel)
        futureSelectedNode?.selectAction()
    }

    fun runAutomation(hideApplication: Boolean = true) {
        try {
            val schemaGroupModel = schemaGroupController.toModel()
            val runTask = createRunTask(schemaGroupModel, hideApplication)
            Thread(runTask).start()
        } catch (e: ApplicationException) { // TODO global catch
            LOGGER.error("ApplicationException: {}", e)
            Platform.runLater {
                val dialog = ExceptionHandlerDialog(e)
                dialog.show()
            }
        } catch (e: Exception) {
            LOGGER.error("Exception: {}", e)
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
                        Platform.runLater { Main.mainStage.isIconified = true }
                    }
                    schemaGroupModel.runAction()
                    Thread.sleep(200)
                } catch (e: ApplicationException) {
                    LOGGER.error("ApplicationException: {}", e)
                    Platform.runLater {
                        val dialog = ExceptionHandlerDialog(e)
                        dialog.show()
                    }
                } catch (e: Exception) {
                    LOGGER.error("Exception: {}", e)
                } finally {
                    if (hideApplication) {
                        Platform.runLater { Main.mainStage.isIconified = false }
                    }
                    automationRunningProperty.set(false)
                }
                return null
            }
        }
    }


    fun getSelectedSchemaGroupModel(): SchemaGroupController {
        val selectedModelVal = selectedModel
        return if (selectedModelVal is SchemaGroupController) {
            selectedModelVal
        } else {
            selectedModelVal.parent ?: schemaGroupController
        }
    }

    fun openLocalSettings() {
        val localSettingsController = LocalSettingsController(controller, localSettings)
        // TODO refactor animation
        localSettingsController.translateXProperty().set(Main.mainStage.scene.width)
        controller.children.add(localSettingsController)

        val timeline = Timeline()
        val kv = KeyValue(localSettingsController.translateXProperty(), 0, Interpolator.EASE_IN)
        val kf = KeyFrame(Duration.seconds(0.1), kv)
        timeline.keyFrames.add(kf)
        timeline.setOnFinished { controller.children.remove(controller.rootBorderPane) }
        timeline.play()
    }

//    fun addActionBlocks(actionBlock: AutomationController) {
//        schemaGroupController.addActionBlocks(actionBlock)
//    }

    fun addActionBlocks(actionController: AutomationController<*>) {
        val selectedModelVal = selectedModel
        when (selectedModelVal) {
            is SchemaGroupController -> selectedModelVal.addActionBlockToBottom(actionController)
            else -> selectedModelVal.addActionBlockUnder(actionController)
        }
        actionController.selectAction()
    }

    private fun loadControllerContent() {
        controller.insidePane.content = schemaGroupController
        schemaGroupController.minHeightProperty().bind(controller.heightProperty())

        automationRunningProperty.addListener { _, _, newValue ->
            Platform.runLater {
                if (newValue) {
                    controller.actionBarController.setStopIcon()
                } else {
                    controller.actionBarController.setRunIcon()
                }
            }
        }
    }

    fun saveTmpFile() {
        TmpFileLoader.saveTmpFile(this)
    }

    fun changeDetect() {
        if (loaded) {
            if (saved) {
                saved = false
                GlobalSettingsLoader.save()
            }
            saved = false
            saveTmpFile()
            Main.mainController?.changeDetect(this)
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

}