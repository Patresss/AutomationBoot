package com.patres.automation.action.keyboard

import com.patres.automation.action.AbstractAction
import com.patres.automation.gui.custom.KeyboardField
import com.patres.automation.type.ActionBootKeyboard


abstract class KeyboardButtonAction(
        private val keyboardField: KeyboardField,
        actionBootKeyboard: ActionBootKeyboard
) : AbstractAction(actionBootKeyboard) {

    val keyValues
        get() = keyboardField.keys.map { it.keyValue }

    override fun toStringLog() = "Action: `$actionBoot` | keyValues: `$keyValues`"

}