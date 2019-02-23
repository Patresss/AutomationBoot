package com.patres.automation.gui.controller.model

import javafx.fxml.FXML
import javafx.scene.Node
import javafx.scene.control.Label
import javafx.scene.layout.BorderPane
import javafx.scene.layout.Pane

open class LabelActionController : AutomationController() {

    @FXML
    lateinit var mainPane: BorderPane

    @FXML
    lateinit var actionLabel: Label

    fun getMainNode(): Node = mainPane

    fun getMainInsideNode(): Pane = mainPane

}
