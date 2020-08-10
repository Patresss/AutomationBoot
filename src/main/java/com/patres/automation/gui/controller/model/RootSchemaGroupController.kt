package com.patres.automation.gui.controller.model

import com.patres.automation.ApplicationLauncher
import com.patres.automation.action.ActionRunner
import com.patres.automation.file.TmpFileLoader
import com.patres.automation.gui.animation.SliderAnimation
import com.patres.automation.gui.controller.ActionBarController
import com.patres.automation.gui.controller.box.AbstractBox
import com.patres.automation.gui.controller.box.SchemaGroupController
import com.patres.automation.gui.controller.saveBackScreen.settings.LocalSettingsController
import com.patres.automation.gui.dialog.LogManager
import com.patres.automation.gui.dialog.SaveRecordedActionsDialog
import com.patres.automation.listener.record.ActionRecorder
import com.patres.automation.mapper.SchemaGroupMapper
import com.patres.automation.settings.LanguageManager
import javafx.application.Platform
import javafx.concurrent.Task
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.control.ScrollPane
import javafx.scene.layout.BorderPane
import javafx.scene.layout.StackPane
import javafx.scene.layout.VBox


open class RootSchemaGroupController(
        val actionRunner: ActionRunner,
        val schemaGroupController: SchemaGroupController
) : StackPane() {

    @FXML
    lateinit var rootBorderPane: BorderPane

    @FXML
    lateinit var insidePane: ScrollPane

    @FXML
    lateinit var actionBox: VBox

    val actionRecorder = ActionRecorder()

    lateinit var actionBarController: ActionBarController

    var loaded: Boolean = false

    private val localSettingsController = LocalSettingsController(this, actionRunner.localSettings)

    private val allChildrenAbstractBlocksRoot: List<AbstractBox<*>>
        get() = schemaGroupController.allChildrenActionBlocks + schemaGroupController

    var selectedModel: AbstractBox<*> = schemaGroupController
        set(value) {
            field = value
            actionBarController.updateDisabledButtons()
        }


    init {
        val fxmlLoader = FXMLLoader(javaClass.getResource("/fxml/RootSchemaGroup.fxml"))
        fxmlLoader.setRoot(this)
        fxmlLoader.setController(this)
        fxmlLoader.resources = LanguageManager.getBundle()
        fxmlLoader.load<RootSchemaGroupController>()
        loadControllerContent()
    }

    fun initialize() {
        actionBarController = ActionBarController(this)
        actionBarController.initAfterSetModel()
    }

    fun unselectAllButton() {
        allChildrenAbstractBlocksRoot.forEach { it.unselectSelectActionButton() }
    }

    fun duplicateSelectedModel() {
        val copy = selectedModel.toSerialized().serializedToController()
        addActionBlocks(copy)
    }

    fun removeSelectedModel() {
        val futureSelectedNode = selectedModel.findNodeOnTheTop()
        selectedModel.schemaGroupParent?.removeNode(selectedModel)
        futureSelectedNode?.selectAction()
    }

    private fun loadControllerContent() {
        selectedModel = schemaGroupController
        insidePane.content = schemaGroupController
        schemaGroupController.minHeightProperty().bind(heightProperty())

        actionRunner.automationRunningProperty.addListener { _, _, newValue ->
            Platform.runLater {
                if (newValue) {
                    actionBarController.setStopIcon()
                } else {
                    actionBarController.setRunIcon()
                }
            }
        }
    }

    fun openLocalSettings() {
        if (!this.children.contains(localSettingsController)) {
            localSettingsController.reload()
            SliderAnimation.goToTheWindow(localSettingsController, rootBorderPane, this)
        }
    }

    fun addActionBlocks(actionBox: AbstractBox<*>) {
        val selectedModelVal = selectedModel
        selectedModelVal.addNewAction(actionBox)
        actionBox.selectAction()
    }


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
            SaveRecordedActionsDialog(recordedActions, this).showDialog()
        }
    }


    fun changeDetect() {
        if (loaded) {
            saveTmpFile()
            ApplicationLauncher.mainController?.changeDetect(this)
        }
    }

    private fun saveTmpFile() {
        TmpFileLoader.saveTmpFile(this)
    }

    fun hasActions() = schemaGroupController.abstractBlocks.isNotEmpty()

    fun runAutomation() {
        try {
            actionRunner.runAutomation(SchemaGroupMapper.controllerToModel(schemaGroupController, actionRunner.automationRunningProperty, emptySet()))
        } catch (e: Exception) {
            LogManager.showAndLogException(e)
        }
    }

    fun stopAutomation() {
        actionRunner.stopAutomation()
    }

}