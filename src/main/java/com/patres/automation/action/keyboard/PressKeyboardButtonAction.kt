package com.patres.automation.action.keyboard

import com.patres.automation.keyboard.KeyboardKey
import com.patres.automation.type.ActionBootKeyboard


class PressKeyboardButtonAction(
        keyboardKeys: List<KeyboardKey>
) : KeyboardButtonAction(keyboardKeys, ActionBootKeyboard.PRESS_KEYBOARD_BUTTON) {

    override fun runAction() {
        keyValues
                .forEach { robot.keyPress(it) }

        keyValues
                .reversed()
                .forEach { robot.keyRelease(it) }
    }

}