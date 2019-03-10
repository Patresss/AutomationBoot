package com.patres.automation.gui.controller.model

import com.jfoenix.controls.JFXTextField
import com.patres.automation.model.AutomationModel
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.scene.Node
import javafx.scene.layout.*
import javafx.scene.layout.StackPane


class SchemaGroupController(model: AutomationModel<out SchemaGroupController>) : AutomationController(model, "SchemaGroup.fxml") {

    @FXML
    lateinit var innerBox: VBox

    @FXML
    lateinit var groupNameTextField: JFXTextField

    @FXML
    lateinit var iterationsTextField: JFXTextField

    @FXML
    override fun initialize() {
        super.initialize()
        groupNameTextField.onMouseClicked = EventHandler { selectAction() }
    }

    fun getMainNode(): Node = this

    fun getMainInsideNode(): Pane = innerBox

    override fun getNodesToSelect(): List<Node> = super.getNodesToSelect() + listOf(groupNameTextField, iterationsTextField)

}