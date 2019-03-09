package com.patres.automation.gui.controller.model

import com.jfoenix.controls.JFXButton
import com.patres.automation.model.AutomationModel
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.scene.Node
import javafx.scene.layout.StackPane


abstract class AutomationController {

    @FXML
    lateinit var selectActionButton: JFXButton

    @FXML
    lateinit var selectStackPane: StackPane

    lateinit var model: AutomationModel<out AutomationController>

    @FXML
    open fun initialize() {
        getNodesToSelect().forEach { it.onMouseClicked = EventHandler { selectAction() } }
    }

    fun afterInit() {
    }


    fun selectAction() {
        model.root.unselectAllButton()
        selectActionButton.styleClass.add("select-action-button-selected")
        model.root.selectedModel = model

    }

    fun unselectAction() {
        selectActionButton.styleClass.remove("select-action-button-selected")
    }

    open fun getNodesToSelect(): List<Node> = listOf(selectStackPane)

}