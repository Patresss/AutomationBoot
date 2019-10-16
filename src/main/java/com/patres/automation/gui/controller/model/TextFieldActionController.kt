package com.patres.automation.gui.controller.model

import com.patres.automation.action.AbstractAction
import com.patres.automation.action.delay.DelayAction
import com.patres.automation.action.mouse.ScrollWheelDownAction
import com.patres.automation.action.mouse.ScrollWheelUpAction
import com.patres.automation.model.RootSchemaGroupModel
import com.patres.automation.type.ActionBootTextField

class TextFieldActionController(
        root: RootSchemaGroupModel,
        parent: SchemaGroupController,
        action: ActionBootTextField,
        fxmlFile: String = "TextFieldAction.fxml"
) : TextActionController<ActionBootTextField>(fxmlFile, root, parent, action) {

    override fun toModel(): AbstractAction {
        action.validation()?.check(value)
        return when (action) {
            ActionBootTextField.DELAY -> DelayAction(value.toInt(), root.automationRunningProperty)
            ActionBootTextField.SCROLL_WHEEL_UP -> ScrollWheelUpAction(value.toInt())
            ActionBootTextField.SCROLL_WHEEL_DOWN -> ScrollWheelDownAction(value.toInt())
        }
    }

}