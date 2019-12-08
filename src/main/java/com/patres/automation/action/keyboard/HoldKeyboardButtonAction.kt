package com.patres.automation.action.keyboard

import com.patres.automation.gui.custom.KeyboardField
import com.patres.automation.type.ActionBootKeyboard


class HoldKeyboardButtonAction(
        keyboardField: KeyboardField
) : KeyboardButtonAction(keyboardField, ActionBootKeyboard.HOLD_KEYBOARD_BUTTON) {

    override fun runAction() {
        keyValues.forEach { robot.keyPress(it) }
    }

}