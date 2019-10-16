package com.patres.automation.action.keyboard

import com.patres.automation.type.ActionBootKeyboard
import com.patres.automation.action.AbstractAction
import com.patres.automation.gui.custom.KeyboardField


class PressKeyboardButtonAction(
        private val keyboardField: KeyboardField
) : AbstractAction(ActionBootKeyboard.PRESS_KEYBOARD_BUTTON) {

    override fun runAction() {
        keyboardField.keys
                .map { it.keyValue }
                .forEach { robot.keyPress(it) }

        keyboardField.keys
                .reversed()
                .map { it.keyValue }
                .forEach { robot.keyRelease(it) }
    }

}