package com.patres.automation.action.keyboard

import com.patres.automation.gui.controller.box.AbstractBox
import com.patres.automation.gui.controller.model.AutomationController
import com.patres.automation.keyboard.KeyboardKey
import com.patres.automation.type.ActionBootKeyboard


class HoldKeyboardButtonAction(
        keyboardKeys: List<KeyboardKey>,
        box: AbstractBox<*>?
) : KeyboardButtonAction(keyboardKeys, ActionBootKeyboard.HOLD_KEYBOARD_BUTTON, box) {

    override fun runAction() {
        keyValues.forEach { robot.keyPress(it) }
    }

}