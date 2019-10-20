package com.patres.automation.gui.controller.model

import com.patres.automation.Main
import com.patres.automation.action.RootSchemaGroupModel
import com.patres.automation.gui.animation.SliderAnimation
import com.patres.automation.gui.controller.ActionBarController
import com.patres.automation.gui.controller.settings.LocalSettingsController
import com.patres.automation.gui.custom.KeyboardButton
import javafx.animation.Interpolator
import javafx.animation.KeyFrame
import javafx.animation.KeyValue
import javafx.animation.Timeline
import javafx.application.Platform
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.control.ScrollPane
import javafx.scene.layout.BorderPane
import javafx.scene.layout.StackPane
import javafx.scene.layout.VBox
import javafx.util.Duration


open class RootSchemaGroupController(
        val model: RootSchemaGroupModel
) : StackPane() {

    init {
        val fxmlLoader = FXMLLoader(javaClass.getResource("/fxml/RootSchemaGroup.fxml"))
        fxmlLoader.setRoot(this)
        fxmlLoader.setController(this)
        fxmlLoader.resources = Main.getBundle()
        fxmlLoader.load<KeyboardButton>()
    }

    @FXML
    lateinit var rootBorderPane: BorderPane

    @FXML
    lateinit var insidePane: ScrollPane

    @FXML
    lateinit var actionBox: VBox

    lateinit var actionBarController: ActionBarController

    private val allChildrenActionBlocksRoot
        get() = schemaGroupController.allChildrenActionBlocks + schemaGroupController

    var schemaGroupController = SchemaGroupController()
        set(value) {
            field = value
            loadControllerContent()
        }

    var selectedModel: AutomationController<*> = schemaGroupController
        set(value) {
            field = value
            actionBarController.updateDisabledButtons()
        }

    init {
        loadControllerContent()
    }

    fun initialize() {
        actionBarController = ActionBarController(this)
        actionBarController.initAfterSetModel()
    }

    fun unselectAllButton() {
        allChildrenActionBlocksRoot.forEach { it.unselectSelectActionButton() }
    }

    fun removeSelectedModel() {
        val futureSelectedNode = selectedModel.findNodeOnTheTop()
        selectedModel.schemaGroupParent?.removeNode(selectedModel)
        futureSelectedNode?.selectAction()
    }

    fun loadControllerContent() {
        selectedModel = schemaGroupController
        insidePane.content = schemaGroupController
        schemaGroupController.minHeightProperty().bind(heightProperty())

        model.automationRunningProperty.addListener { _, _, newValue ->
            Platform.runLater {
                if (newValue) {
                    actionBarController.setStopIcon()
                } else {
                    actionBarController.setRunIcon()
                }
            }
        }
    }

    fun getSelectedSchemaGroupModel(): SchemaGroupController {
        val selectedModelVal = selectedModel
        return if (selectedModelVal is SchemaGroupController) {
            selectedModelVal
        } else {
            selectedModelVal.schemaGroupParent ?: schemaGroupController
        }
    }

    fun openLocalSettings() {
        val localSettingsController = LocalSettingsController(this, model.localSettings)
        SliderAnimation.goToTheWindow(localSettingsController, rootBorderPane, this)
    }

    fun addActionBlocks(actionController: AutomationController<*>) {
        val selectedModelVal = selectedModel
        when (selectedModelVal) {
            is SchemaGroupController -> selectedModelVal.addActionBlockToBottom(actionController)
            else -> selectedModelVal.addActionBlockUnder(actionController)
        }
        actionController.selectAction()
    }

}