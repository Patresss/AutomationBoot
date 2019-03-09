package com.patres.automation.gui.controller.model

import com.patres.automation.gui.custom.KeyboardField
import javafx.fxml.FXML
import javafx.scene.Node

open class KeyboardButtonActionController : LabelActionController() {

    @FXML
    lateinit var keyboardField: KeyboardField

    override fun getNodesToSelect(): List<Node> = super.getNodesToSelect() + listOf(keyboardField)

}
