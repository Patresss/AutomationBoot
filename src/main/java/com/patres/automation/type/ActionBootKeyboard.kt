package com.patres.automation.type

import com.patres.automation.ActionBootControllerType
import com.patres.automation.action.AbstractAction
import com.patres.automation.action.keyboard.PressKeyboardButtonAction
import com.patres.automation.gui.controller.model.AutomationController
import com.patres.automation.gui.controller.model.KeyboardButtonActionController
import com.patres.automation.gui.custom.KeyboardField
import com.patres.automation.model.RootSchemaGroupModel
import com.patres.automation.validation.Validationable


enum class ActionBootKeyboard(
        val bundleName: String,
        val validation: Validationable? = null,
        val controllerType: ActionBootControllerType,
        val createModel: (KeyboardField) -> AbstractAction
) : ActionBootable {

    PRESS_KEYBOARD_BUTTON("robot.action.keyboard.press", null, ActionBootControllerType.KEYBOARD_BUTTON, { keyboardField: KeyboardField -> PressKeyboardButtonAction(keyboardField) });

    override fun validation(): Validationable? {
        return this.validation
    }

    override fun bundleName(): String {
        return this.bundleName
    }

    override fun controllerType(): ActionBootControllerType {
        return this.controllerType
    }

    override fun createController(): (RootSchemaGroupModel) -> AutomationController<*> {
        return { root: RootSchemaGroupModel -> KeyboardButtonActionController(root, root.controller.getSelectedSchemaGroupModel(), this) }
    }

}
