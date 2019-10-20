package com.patres.automation.type

import com.patres.automation.ActionBootControllerType
import com.patres.automation.gui.controller.model.AutomationController
import com.patres.automation.gui.controller.model.TextFieldActionController
import com.patres.automation.validation.PositiveIntegerValidation
import com.patres.automation.validation.Validationable


enum class ActionBootTextField(
        val bundleName: String,
        val validation: Validationable? = null,
        val controllerType: ActionBootControllerType
) : ActionBootable {

    DELAY("robot.action.delay", PositiveIntegerValidation(), ActionBootControllerType.TEXT_FIELD),

    SCROLL_WHEEL_UP("robot.action.scrollWheel.up", PositiveIntegerValidation(), ActionBootControllerType.TEXT_FIELD),
    SCROLL_WHEEL_DOWN("robot.action.scrollWheel.down", PositiveIntegerValidation(), ActionBootControllerType.TEXT_FIELD);


    override fun validation(): Validationable? {
        return this.validation
    }

    override fun bundleName(): String {
        return this.bundleName
    }

    override fun controllerType(): ActionBootControllerType {
        return this.controllerType
    }

    override fun createController(): () -> AutomationController<*> = { TextFieldActionController(this) }

}
