package com.patres.automation.gui.controller.model

import com.jfoenix.controls.JFXButton
import com.patres.automation.action.BrowseFileAction
import javafx.fxml.FXML
import javafx.scene.Node

class BrowseFileActionController(
        model: BrowseFileAction,
        fxmlFile: String = "BrowsFIleAction.fxml"
) : TextFieldActionController(model, fxmlFile) {

    @FXML
    lateinit var browseFileButton: JFXButton

    override fun getNodesToSelect(): List<Node> = super.getNodesToSelect() + listOf(browseFileButton)

    @FXML
    override fun initialize() {
        super.initialize()
    }

}
