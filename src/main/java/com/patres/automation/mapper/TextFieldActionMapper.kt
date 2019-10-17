package com.patres.automation.mapper

import com.patres.automation.action.AbstractAction
import com.patres.automation.action.delay.DelayAction
import com.patres.automation.action.mouse.ScrollWheelDownAction
import com.patres.automation.action.mouse.ScrollWheelUpAction
import com.patres.automation.gui.controller.model.SchemaGroupController
import com.patres.automation.gui.controller.model.TextFieldActionController
import com.patres.automation.mapper.model.TextFieldActionSerialized
import com.patres.automation.model.RootSchemaGroupModel
import com.patres.automation.type.ActionBootTextField

object TextFieldActionMapper : Mapper<TextFieldActionController, AbstractAction, TextFieldActionSerialized> {

    override fun controllerToModel(controller: TextFieldActionController): AbstractAction {
        return when (controller.action) {
            ActionBootTextField.DELAY -> DelayAction(controller.value.toInt(), controller.root.automationRunningProperty)
            ActionBootTextField.SCROLL_WHEEL_UP -> ScrollWheelUpAction(controller.value.toInt())
            ActionBootTextField.SCROLL_WHEEL_DOWN -> ScrollWheelDownAction(controller.value.toInt())
        }
    }

    override fun controllerToSerialized(controller: TextFieldActionController): TextFieldActionSerialized {
        return controller.run {
            TextFieldActionSerialized(action, value)
        }
    }

    override fun serializedToController(serialized: TextFieldActionSerialized, root: RootSchemaGroupModel, parent: SchemaGroupController?): TextFieldActionController {
        return TextFieldActionController(root, parent, serialized.actionType).apply {
            value = serialized.value
        }
    }

}