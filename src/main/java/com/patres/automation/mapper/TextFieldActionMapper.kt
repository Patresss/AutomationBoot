package com.patres.automation.mapper

import com.patres.automation.action.AbstractAction
import com.patres.automation.action.mouse.ScrollWheelDownAction
import com.patres.automation.action.mouse.ScrollWheelUpAction
import com.patres.automation.excpetion.ControllerCannotBeMapToModelException
import com.patres.automation.gui.controller.model.TextFieldActionController
import com.patres.automation.mapper.model.TextFieldActionSerialized
import com.patres.automation.type.ActionBootTextField
import com.patres.automation.type.ActionBootTextField.*
import javafx.beans.property.BooleanProperty

object TextFieldActionMapper : Mapper<TextFieldActionController, AbstractAction, TextFieldActionSerialized> {

    override fun controllerToModel(controller: TextFieldActionController): AbstractAction {
        return calculateTextFieldModel(controller.actionBoot, controller.value.toInt())

    }

    override fun controllerToSerialized(controller: TextFieldActionController): TextFieldActionSerialized {
        return controller.run {
            TextFieldActionSerialized(actionBoot, value)
        }
    }

    override fun serializedToController(serialized: TextFieldActionSerialized): TextFieldActionController {
        return TextFieldActionController(serialized.actionBootType).apply {
            value = serialized.value
        }
    }

    override fun serializedToModel(serialized: TextFieldActionSerialized, automationRunningProperty: BooleanProperty?): AbstractAction {
        return calculateTextFieldModel(serialized.actionBootType, serialized.value.toInt())
    }

    private fun calculateTextFieldModel(actionType: ActionBootTextField, value: Int): AbstractAction {
        return when (actionType) {
            SCROLL_WHEEL_UP -> ScrollWheelUpAction(value)
            SCROLL_WHEEL_DOWN -> ScrollWheelDownAction(value)

            ENDPOINT_NAME -> throw ControllerCannotBeMapToModelException(actionType)
            PORT -> throw ControllerCannotBeMapToModelException(actionType)
            SERVER_USERNAME -> throw ControllerCannotBeMapToModelException(actionType)
        }
    }


}