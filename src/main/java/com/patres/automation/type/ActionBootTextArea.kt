package com.patres.automation.type

import com.patres.automation.ActionBootControllerType
import com.patres.automation.gui.controller.model.AutomationController
import com.patres.automation.gui.controller.model.TextAreaActionController
import com.patres.automation.gui.controller.model.TextFieldActionController
import com.patres.automation.model.RootSchemaGroupModel
import com.patres.automation.validation.IntegerValidation
import com.patres.automation.validation.Validationable


enum class ActionBootTextArea(
        val bundleName: String,
        val validation: Validationable? = null,
        val controllerType: ActionBootControllerType
) : ActionBootable {

    PASTE_TEXT("robot.action.keyboard.paste", null, ActionBootControllerType.TEXT_AREA),
    TYPE_TEXT("robot.action.keyboard.type", null, ActionBootControllerType.TEXT_AREA);

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
        return { root: RootSchemaGroupModel -> TextAreaActionController(root, root.getSelectedSchemaGroupModel(), this) }
    }

}
