package com.patres.automation.mapper

import com.patres.automation.action.AbstractAction
import com.patres.automation.action.mouse.ScrollWheelDownAction
import com.patres.automation.action.mouse.ScrollWheelUpAction
import com.patres.automation.excpetion.ControllerCannotBeMapToModelException
import com.patres.automation.gui.controller.box.AbstractBox
import com.patres.automation.gui.controller.model.TextFieldActionController
import com.patres.automation.mapper.model.TextFieldActionSerialized
import com.patres.automation.parameter.received.ReceivedParameterConverter
import com.patres.automation.parameter.sent.SentParameter
import com.patres.automation.parameter.sent.SentParameterConverter
import com.patres.automation.type.ActionBootTextField
import com.patres.automation.type.ActionBootTextField.*
import javafx.beans.property.BooleanProperty

object TextFieldActionMapper : Mapper<TextFieldActionController, AbstractAction, TextFieldActionSerialized> {

    override fun controllerToModel(controller: TextFieldActionController, automationRunningProperty: BooleanProperty, parameters: Set<SentParameter>): AbstractAction {
        return calculateTextFieldModel(controller.actionBoot, controller.calculateParametrizedValue(parameters).toInt(), controller.box)
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

    override fun serializedToModel(serialized: TextFieldActionSerialized, automationRunningProperty: BooleanProperty, parameters: Set<SentParameter>): AbstractAction {
        val parametrizedValue = ReceivedParameterConverter(serialized.value, parameters).replaceValue()
        return calculateTextFieldModel(serialized.actionBootType, parametrizedValue.toInt())
    }

    private fun calculateTextFieldModel(actionType: ActionBootTextField, value: Int, box: AbstractBox<*>? = null): AbstractAction {
        return when (actionType) {
            SCROLL_WHEEL_UP -> ScrollWheelUpAction(value, box)
            SCROLL_WHEEL_DOWN -> ScrollWheelDownAction(value, box)

            ENDPOINT_NAME -> throw ControllerCannotBeMapToModelException(actionType)
            PORT -> throw ControllerCannotBeMapToModelException(actionType)
            SERVER_USERNAME -> throw ControllerCannotBeMapToModelException(actionType)
        }
    }


}