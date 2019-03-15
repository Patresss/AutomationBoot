package com.patres.automation.gui.controller.model

import com.jfoenix.controls.JFXButton
import com.patres.automation.Main
import com.patres.automation.gui.custom.KeyboardButton
import com.patres.automation.model.AutomationModel
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Node
import javafx.scene.control.Button
import javafx.scene.layout.GridPane
import javafx.scene.layout.StackPane


abstract class AutomationController(
        val model: AutomationModel<out AutomationController>? = null,
        fxmlFile: String
) : StackPane() {

    init {
        val fxmlLoader = FXMLLoader(javaClass.getResource("/fxml/$fxmlFile"))
        fxmlLoader.setRoot(this)
        fxmlLoader.setController(this)
        fxmlLoader.resources = Main.bundle
        fxmlLoader.load<KeyboardButton>()
    }

    @FXML
    lateinit var selectActionButton: JFXButton

    @FXML
    lateinit var selectStackPane: StackPane

    @FXML
    lateinit var gridPane: GridPane

    @FXML
    open fun initialize() {
        if(model == null) {
            gridPane.children.remove(selectStackPane)
        } else {
            getNodesToSelect().forEach { it.onMouseClicked = EventHandler { selectAction() } }
        }
    }

    fun afterInit() {
    }


    fun selectAction() {
        model?.root?.unselectAllButton()
        selectActionButton.styleClass.add("select-action-button-selected")
        model?.root?.selectedModel = model

    }

    fun unselectAction() {
        selectActionButton.styleClass.remove("select-action-button-selected")
    }

    open fun getNodesToSelect(): List<Node> = listOf(selectStackPane)

}