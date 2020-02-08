package com.patres.automation.action.keyboard

import com.patres.automation.keyboard.KeyboardKey
import com.patres.automation.type.ActionBootKeyboard


class ReleaseKeyboardButtonAction(
        keyboardKeys: List<KeyboardKey>
) : KeyboardButtonAction(keyboardKeys, ActionBootKeyboard.RELEASE_KEYBOARD_BUTTON) {

    override fun runAction() {
        keyValues.forEach { robot.keyPress(it) }
    }

}