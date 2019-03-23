package com.patres.automation.gui.controller.model

import com.patres.automation.gui.custom.KeyboardField
import com.patres.automation.model.AutomationModel
import javafx.beans.InvalidationListener
import javafx.fxml.FXML
import javafx.scene.Node

open class KeyboardButtonActionController(
        model: AutomationModel<out KeyboardButtonActionController>? = null,
        fxmlFile: String = "KeyboardFieldAction.fxml",
        labelText: String = ""
) : LabelActionController(model, fxmlFile, labelText) {

    @FXML
    lateinit var keyboardField: KeyboardField

    @FXML
    override fun initialize() {
        super.initialize()
    }

    init {
        keyboardField.keys.addListener(InvalidationListener { model?.root?.changeDetect() })
    }

    override fun getNodesToSelect(): List<Node> = super.getNodesToSelect() + listOf(keyboardField)

}
