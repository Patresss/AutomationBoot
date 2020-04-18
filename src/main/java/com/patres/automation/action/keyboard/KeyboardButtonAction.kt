package com.patres.automation.action.keyboard

import com.patres.automation.action.AbstractAction
import com.patres.automation.keyboard.KeyboardKey
import com.patres.automation.type.ActionBootKeyboard


abstract class KeyboardButtonAction(
        val keyboardKeys: List<KeyboardKey>,
        actionBootKeyboard: ActionBootKeyboard
) : AbstractAction(actionBootKeyboard) {

    val keyValues
        get() = keyboardKeys.map { it.keyValue }

    override fun toStringLog() = "Action: `$actionBootType` | keyValues: `$keyboardKeys`"

}