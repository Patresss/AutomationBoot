package com.patres.automation.gui.controller.model

import com.jfoenix.controls.JFXButton
import com.patres.automation.Main
import com.patres.automation.gui.controller.PointerController
import javafx.fxml.FXML
import javafx.scene.Node
import javafx.scene.Scene
import javafx.stage.Stage
import javafx.stage.StageStyle

class BrowseFileActionController : TextFieldActionController() {

    @FXML
    lateinit var browseFileButton: JFXButton

    override fun getNodesToSelect(): List<Node> = super.getNodesToSelect() + listOf(browseFileButton)

    @FXML
    override fun initialize() {
        super.initialize()
    }

}
