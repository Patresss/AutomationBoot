package com.patres.automation.model

import com.patres.automation.Main
import com.patres.automation.excpetion.ApplicationException
import com.patres.automation.file.TmpFileLoader
import com.patres.automation.gui.controller.LocalSettingsController
import com.patres.automation.gui.controller.model.AutomationController
import com.patres.automation.gui.controller.model.RootSchemaGroupController
import com.patres.automation.gui.dialog.ExceptionHandlerDialog
import com.patres.automation.keyboard.listener.RootSchemaKeyListener
import com.patres.automation.settings.GlobalSettingsLoader
import com.patres.automation.settings.LocalSettings
import javafx.animation.Interpolator
import javafx.animation.KeyFrame
import javafx.animation.KeyValue
import javafx.animation.Timeline
import javafx.application.Platform
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

    var loaded: Boolean = false

    val controller: RootSchemaGroupController = RootSchemaGroupController(this)

    var robot = Robot()

    val rootSchemaKeyListener = RootSchemaKeyListener(this)

    var schemaGroup = SchemaGroupModel(this, null)
        set(value) {
            field = value
            loadControllerContent()
        }

    var selectedModel: AutomationModel<out AutomationController>? = null
        set(value) {
            field = value
            controller.actionBarController.updateDisabledButtons()
        }

    private val allChildrenActionBlocks
        get() = schemaGroup.allChildrenActionBlocks

    private val allChildrenActionBlocksRoot
        get() = allChildrenActionBlocks + schemaGroup

    init {
        controller.actionBarController.initAfterSetModel()
        loadControllerContent()
    }

    fun unselectAllButton() {
        allChildrenActionBlocksRoot.forEach { it.unselectSelectActionButton() }
    }

    fun removeSelectedModel() {
        val futureSelectedNode = selectedModel?.findNodeOnTheTop()
        selectedModel?.let {
            it.parent?.removeNode(it)
            futureSelectedNode?.controller?.selectAction()
        }
    }

    fun addNodeAction(textActionModel: ActionNodeModel<out AutomationController>) {
        addNewActionModel(textActionModel)
    }

    fun addSchemaGroup() {
        val schemaGroupModel = SchemaGroupModel(this, schemaGroup)
        addNewActionModel(schemaGroupModel)
    }

    fun runAutomation(hideApplication: Boolean = true) {
        val runTask = createRunTask(hideApplication)
        Thread(runTask).start()
    }

    private fun createRunTask(hideApplication: Boolean = false): Task<Void> {
        return object : Task<Void>() {
            override fun call(): Void? {
                try {
                    rootSchemaKeyListener.reset()
                    schemaGroup.checkValidation()
                    if (hideApplication) {
                        Platform.runLater { Main.mainStage.isIconified = true }
                    }
                    schemaGroup.runAction()
                    Thread.sleep(200)
                } catch (e: ApplicationException) {
                    LOGGER.error("ApplicationException: {}", e.message)
                    val dialog = ExceptionHandlerDialog(e)
                    dialog.show()
                } catch (e: Exception) {
                    LOGGER.error("Exception: {}", e)
                } finally {
                    if (hideApplication) {
                        Platform.runLater { Main.mainStage.isIconified = false }
                    }
                }
                return null
            }
        }
    }


    fun getSelectedSchemaGroupModel(): SchemaGroupModel {
        val selectedModelVal = selectedModel
        return if (selectedModelVal != null && selectedModelVal is SchemaGroupModel) {
            selectedModelVal
        } else {
            schemaGroup
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

    private fun addActionBlocks(actionBlock: AutomationModel<out AutomationController>) {
        schemaGroup.addActionBlocks(actionBlock)
    }

    private fun addNewActionModel(actionModel: AutomationModel<out AutomationController>) {
        val selectedModelVal = selectedModel
        when (selectedModelVal) {
            null -> addActionBlocks(actionModel)
            is SchemaGroupModel -> selectedModelVal.moveActionBlockToBottom(actionModel)
            is ActionNodeModel -> selectedModelVal.addActionBlockUnder(actionModel)
        }
        actionModel.controller.selectAction()
    }

    private fun loadControllerContent() {
        controller.insidePane.content = schemaGroup.controller
        schemaGroup.controller.minHeightProperty().bind(controller.heightProperty())
    }

    private fun saveTmpFile() {
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

}