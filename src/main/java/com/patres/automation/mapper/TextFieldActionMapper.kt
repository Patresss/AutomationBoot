package com.patres.automation.mapper

import com.patres.automation.action.AbstractAction
import com.patres.automation.action.delay.DelayAction
import com.patres.automation.action.mouse.ScrollWheelDownAction
import com.patres.automation.action.mouse.ScrollWheelUpAction
import com.patres.automation.excpetion.ControllerCannotBeMapToModelException
import com.patres.automation.gui.controller.model.TextFieldActionController
import com.patres.automation.mapper.model.TextFieldActionSerialized
import com.patres.automation.type.ActionBootTextField.*

object TextFieldActionMapper : Mapper<TextFieldActionController, AbstractAction, TextFieldActionSerialized> {

    override fun controllerToModel(controller: TextFieldActionController): AbstractAction {
        return when (controller.action) {
            DELAY -> DelayAction(controller.value.toLong(), controller.root?.automationRunningProperty)
            SCROLL_WHEEL_UP -> ScrollWheelUpAction(controller.value.toInt())
            SCROLL_WHEEL_DOWN -> ScrollWheelDownAction(controller.value.toInt())

            ENDPOINT_NAME -> throw ControllerCannotBeMapToModelException(controller.action)
            PORT -> throw ControllerCannotBeMapToModelException(controller.action)
        }
    }

    override fun controllerToSerialized(controller: TextFieldActionController): TextFieldActionSerialized {
        return controller.run {
            TextFieldActionSerialized(action, value)
        }
    }

    override fun serializedToController(serialized: TextFieldActionSerialized): TextFieldActionController {
        return TextFieldActionController(serialized.actionType).apply {
            value = serialized.value
        }
    }

}