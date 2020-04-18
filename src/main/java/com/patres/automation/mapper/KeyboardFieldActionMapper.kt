package com.patres.automation.mapper

import com.patres.automation.action.keyboard.HoldKeyboardButtonAction
import com.patres.automation.action.keyboard.KeyboardButtonAction
import com.patres.automation.action.keyboard.PressKeyboardButtonAction
import com.patres.automation.action.keyboard.ReleaseKeyboardButtonAction
import com.patres.automation.excpetion.ControllerCannotBeMapToModelException
import com.patres.automation.gui.controller.model.KeyboardButtonActionController
import com.patres.automation.keyboard.KeyboardKey
import com.patres.automation.mapper.model.KeyboardFieldActionSerialized
import com.patres.automation.type.ActionBootKeyboard
import com.patres.automation.type.ActionBootKeyboard.*
import javafx.beans.property.BooleanProperty


object KeyboardFieldActionMapper : Mapper<KeyboardButtonActionController, KeyboardButtonAction, KeyboardFieldActionSerialized> {

    override fun controllerToModel(controller: KeyboardButtonActionController): KeyboardButtonAction {
        return calculateKeyboardFieldAction(controller.actionBoot, controller.keyboardField.keys)
    }

    override fun controllerToSerialized(controller: KeyboardButtonActionController): KeyboardFieldActionSerialized {
        return controller.run {
            KeyboardFieldActionSerialized(actionBoot, keyboardField.keys)
        }
    }

    override fun serializedToController(serialized: KeyboardFieldActionSerialized): KeyboardButtonActionController {
        return KeyboardButtonActionController(serialized.actionBootType).apply {
            serialized.keys.forEach { keyboardField.addKeyboardButton(it) }
        }
    }

    override fun serializedToModel(serialized: KeyboardFieldActionSerialized, automationRunningProperty: BooleanProperty?): KeyboardButtonAction {
        return calculateKeyboardFieldAction(serialized.actionBootType, serialized.keys)
    }

    private fun calculateKeyboardFieldAction(actionType: ActionBootKeyboard, keys: List<KeyboardKey>): KeyboardButtonAction {
        return when (actionType) {
            PRESS_KEYBOARD_BUTTON -> PressKeyboardButtonAction(keys)
            HOLD_KEYBOARD_BUTTON -> HoldKeyboardButtonAction(keys)
            RELEASE_KEYBOARD_BUTTON -> ReleaseKeyboardButtonAction(keys)

            RUN_KEYS_SETTINGS -> throw ControllerCannotBeMapToModelException(actionType)
            STOP_KEYS_SETTINGS -> throw ControllerCannotBeMapToModelException(actionType)
            START_RECORDING_KEYS_SETTINGS -> throw ControllerCannotBeMapToModelException(actionType)
            STOP_RECORDING_KEYS_SETTINGS -> throw ControllerCannotBeMapToModelException(actionType)
        }
    }

}
