package com.patres.automation.action.keyboard

import com.patres.automation.gui.custom.KeyboardField
import com.patres.automation.type.ActionBootKeyboard


class PressKeyboardButtonAction(
        keyboardField: KeyboardField
) : KeyboardButtonAction(keyboardField, ActionBootKeyboard.PRESS_KEYBOARD_BUTTON) {

    override fun runAction() {
        keyValues
                .forEach { robot.keyPress(it) }

        keyValues
                .reversed()
                .forEach { robot.keyRelease(it) }
    }

}