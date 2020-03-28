package com.patres.automation.mapper

import com.patres.automation.action.keyboard.HoldKeyboardButtonAction
import com.patres.automation.action.keyboard.KeyboardButtonAction
import com.patres.automation.action.keyboard.PressKeyboardButtonAction
import com.patres.automation.action.keyboard.ReleaseKeyboardButtonAction
import com.patres.automation.excpetion.ControllerCannotBeMapToModelException
import com.patres.automation.gui.controller.model.KeyboardButtonActionController
import com.patres.automation.mapper.model.KeyboardFieldActionSerialized
import com.patres.automation.type.ActionBootKeyboard.*
import javafx.beans.property.BooleanProperty


object KeyboardFieldActionMapper : Mapper<KeyboardButtonActionController, KeyboardButtonAction, KeyboardFieldActionSerialized> {

    override fun controllerToModel(controller: KeyboardButtonActionController): KeyboardButtonAction {
        return when (controller.action) {
            PRESS_KEYBOARD_BUTTON -> PressKeyboardButtonAction(controller.keyboardField.keys)
            HOLD_KEYBOARD_BUTTON -> HoldKeyboardButtonAction(controller.keyboardField.keys)
            RELEASE_KEYBOARD_BUTTON -> ReleaseKeyboardButtonAction(controller.keyboardField.keys)

            RUN_KEYS_SETTINGS -> throw ControllerCannotBeMapToModelException(controller.action)
            STOP_KEYS_SETTINGS -> throw ControllerCannotBeMapToModelException(controller.action)
            START_RECORDING_KEYS_SETTINGS -> throw ControllerCannotBeMapToModelException(controller.action)
            STOP_RECORDING_KEYS_SETTINGS -> throw ControllerCannotBeMapToModelException(controller.action)
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

    override fun serializedToModel(serialized: KeyboardFieldActionSerialized, automationRunningProperty: BooleanProperty?): KeyboardButtonAction {
        return when (serialized.actionType) {
            PRESS_KEYBOARD_BUTTON -> PressKeyboardButtonAction(serialized.keys)
            HOLD_KEYBOARD_BUTTON -> HoldKeyboardButtonAction(serialized.keys)
            RELEASE_KEYBOARD_BUTTON -> ReleaseKeyboardButtonAction(serialized.keys)

            RUN_KEYS_SETTINGS -> throw ControllerCannotBeMapToModelException(serialized.actionType)
            STOP_KEYS_SETTINGS -> throw ControllerCannotBeMapToModelException(serialized.actionType)
            START_RECORDING_KEYS_SETTINGS -> throw ControllerCannotBeMapToModelException(serialized.actionType)
            STOP_RECORDING_KEYS_SETTINGS -> throw ControllerCannotBeMapToModelException(serialized.actionType)
        }
    }

}
