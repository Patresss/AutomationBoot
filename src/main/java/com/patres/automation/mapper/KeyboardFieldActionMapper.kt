package com.patres.automation.mapper

import com.patres.automation.action.keyboard.HoldKeyboardButtonAction
import com.patres.automation.action.keyboard.KeyboardButtonAction
import com.patres.automation.action.keyboard.PressKeyboardButtonAction
import com.patres.automation.action.keyboard.ReleaseKeyboardButtonAction
import com.patres.automation.excpetion.ControllerCannotBeMapToModelException
import com.patres.automation.gui.controller.model.KeyboardButtonActionController
import com.patres.automation.mapper.model.KeyboardFieldActionSerialized
import com.patres.automation.type.ActionBootKeyboard.*


object KeyboardFieldActionMapper : Mapper<KeyboardButtonActionController, KeyboardButtonAction, KeyboardFieldActionSerialized> {

    override fun controllerToModel(controller: KeyboardButtonActionController): KeyboardButtonAction {
        return when (controller.action) {
            PRESS_KEYBOARD_BUTTON -> PressKeyboardButtonAction(controller.keyboardField)
            HOLD_KEYBOARD_BUTTON -> HoldKeyboardButtonAction(controller.keyboardField)
            RELEASE_KEYBOARD_BUTTON -> ReleaseKeyboardButtonAction(controller.keyboardField)

            RUN_KEYS_SETTINGS -> throw ControllerCannotBeMapToModelException(RUN_KEYS_SETTINGS)
            STOP_KEYS_SETTINGS -> throw ControllerCannotBeMapToModelException(STOP_KEYS_SETTINGS)
        }
    }

    override fun controllerToSerialized(controller: KeyboardButtonActionController): KeyboardFieldActionSerialized {
        return controller.run {
            KeyboardFieldActionSerialized(action, keyboardField.keys)
        }
    }

    override fun serializedToController(serialized: KeyboardFieldActionSerialized): KeyboardButtonActionController {
        return KeyboardButtonActionController(serialized.actionType).apply {
            serialized.keys.forEach { keyboardField.addKeyboardButton(it) }
        }
    }

}
