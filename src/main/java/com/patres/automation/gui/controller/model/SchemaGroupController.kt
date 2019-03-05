package com.patres.automation.gui.controller.model

import com.jfoenix.controls.JFXTextField
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.scene.Node
import javafx.scene.layout.BorderPane
import javafx.scene.layout.Pane
import javafx.scene.layout.StackPane
import javafx.scene.layout.VBox


class SchemaGroupController : AutomationController() {

    @FXML
    lateinit var mainSchemaBox: BorderPane

    @FXML
    lateinit var outsideBox: StackPane

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

    fun getMainNode(): Node = mainSchemaBox

    fun getMainInsideNode(): Pane = innerBox

    override fun getNodesToSelect(): List<Node> = super.getNodesToSelect() + listOf(groupNameTextField, iterationsTextField)

}