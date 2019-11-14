package com.patres.automation.gui.controller.model

import com.patres.automation.gui.custom.KeyboardField
import com.patres.automation.type.ActionBootKeyboard
import javafx.beans.InvalidationListener
import javafx.fxml.FXML

class KeyboardButtonActionController(
        action: ActionBootKeyboard
) : AutomationController<ActionBootKeyboard>("KeyboardFieldAction.fxml", action) {

    @FXML
    lateinit var keyboardField: KeyboardField

    @FXML
    override fun initialize() {
        super.initialize()
    }

    init {
        keyboardField.keys.addListener(InvalidationListener { root?.changeDetect() })
    }

}
