package com.patres.automation.action.keyboard

import com.patres.automation.action.AbstractAction
import com.patres.automation.gui.controller.box.AbstractBox
import com.patres.automation.gui.controller.model.AutomationController
import com.patres.automation.keyboard.KeyboardKey
import com.patres.automation.type.ActionBootKeyboard


abstract class KeyboardButtonAction(
        val keyboardKeys: List<KeyboardKey>,
        actionBootKeyboard: ActionBootKeyboard,
        box: AbstractBox<*>?
) : AbstractAction(actionBootKeyboard, box) {

    val keyValues
        get() = keyboardKeys.map { it.keyValue }

    override fun toStringLog() = "Action: `$actionBootType` | keyValues: `$keyboardKeys`"

}