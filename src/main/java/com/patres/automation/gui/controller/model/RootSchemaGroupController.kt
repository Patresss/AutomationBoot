package com.patres.automation.gui.controller.model

import com.patres.automation.action.RootSchemaGroupModel
import com.patres.automation.gui.animation.SliderAnimation
import com.patres.automation.gui.controller.ActionBarController
import com.patres.automation.gui.controller.box.AbstractBox
import com.patres.automation.gui.controller.box.ActionBox
import com.patres.automation.gui.controller.box.SchemaGroupController
import com.patres.automation.gui.controller.settings.LocalSettingsController
import com.patres.automation.settings.LanguageManager
import javafx.application.Platform
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.control.ScrollPane
import javafx.scene.layout.BorderPane
import javafx.scene.layout.StackPane
import javafx.scene.layout.VBox


open class RootSchemaGroupController(
        val model: RootSchemaGroupModel
) : StackPane() {

    init {
        val fxmlLoader = FXMLLoader(javaClass.getResource("/fxml/RootSchemaGroup.fxml"))
        fxmlLoader.setRoot(this)
        fxmlLoader.setController(this)
        fxmlLoader.resources = LanguageManager.getBundle()
        fxmlLoader.load<RootSchemaGroupController>()
    }

    @FXML
    lateinit var rootBorderPane: BorderPane

    @FXML
    lateinit var insidePane: ScrollPane

    @FXML
    lateinit var actionBox: VBox

    lateinit var actionBarController: ActionBarController

    private val allChildrenAbstractBlocksRoot: List<AbstractBox<*>>
        get() = schemaGroupController.allChildrenActionBlocks + schemaGroupController

    var schemaGroupController = SchemaGroupController()
        set(value) {
            field = value
            loadControllerContent()
        }

    var selectedModel: AbstractBox<*> = schemaGroupController
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
        allChildrenAbstractBlocksRoot.forEach { it.unselectSelectActionButton() }
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

    fun openLocalSettings() {
        val localSettingsController = LocalSettingsController(this, model.localSettings)
        SliderAnimation.goToTheWindow(localSettingsController, rootBorderPane, this)
    }

    fun addActionBlocks(actionController: AutomationController<*>) {
        val actionBox = ActionBox(actionController)
        addActionBlocks(actionBox)
    }

    fun addActionBlocks(actionBox: AbstractBox<*>) {
        val selectedModelVal = selectedModel
        selectedModelVal.addNewAction(actionBox)
        actionBox.selectAction()
    }

}