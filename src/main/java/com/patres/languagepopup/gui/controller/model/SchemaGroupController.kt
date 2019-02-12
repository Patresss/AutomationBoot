package com.patres.languagepopup.gui.controller.model

import com.jfoenix.controls.JFXButton
import com.jfoenix.controls.JFXTextField
import com.patres.languagepopup.model.AutomationModel
import com.patres.languagepopup.model.SchemaGroupModel
import javafx.fxml.FXML
import javafx.scene.Node
import javafx.scene.layout.*


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
    lateinit var selectActionButton: JFXButton

    lateinit var model: SchemaGroupModel

    override fun getModel(): AutomationModel<out AutomationController> = model

    fun initialize() {
    }

    @FXML
    fun selectAction() {
        model.root.unselectAllButton()
        selectActionButton.styleClass.add("select-action-button-selected")
        model.root.selectedModel = model
    }

    fun unselectAction() {
        selectActionButton.styleClass.remove("select-action-button-selected")
    }


    fun getMainOutsideNode(): Node = mainSchemaBox

    fun getMainInsideNode(): Pane = innerBox

}