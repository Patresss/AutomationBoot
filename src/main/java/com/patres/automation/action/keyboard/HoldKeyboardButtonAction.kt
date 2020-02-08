package com.patres.automation.action.keyboard

import com.patres.automation.keyboard.KeyboardKey
import com.patres.automation.type.ActionBootKeyboard


class HoldKeyboardButtonAction(
        keyboardKeys: List<KeyboardKey>
) : KeyboardButtonAction(keyboardKeys, ActionBootKeyboard.HOLD_KEYBOARD_BUTTON) {

    override fun runAction() {
        keyValues.forEach { robot.keyPress(it) }
    }

}