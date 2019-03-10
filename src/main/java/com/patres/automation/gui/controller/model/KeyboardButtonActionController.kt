package com.patres.automation.gui.controller.model

import com.patres.automation.gui.custom.KeyboardField
import com.patres.automation.model.AutomationModel
import javafx.fxml.FXML
import javafx.scene.Node

open class KeyboardButtonActionController(
        model: AutomationModel<out KeyboardButtonActionController>,
        fxmlFile: String = "KeyboardFieldAction.fxml"
) : LabelActionController(model, fxmlFile) {

    @FXML
    lateinit var keyboardField: KeyboardField

    override fun getNodesToSelect(): List<Node> = super.getNodesToSelect() + listOf(keyboardField)

}
