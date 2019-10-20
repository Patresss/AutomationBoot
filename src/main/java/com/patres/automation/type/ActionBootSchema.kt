package com.patres.automation.type

import com.patres.automation.ActionBootControllerType
import com.patres.automation.gui.controller.model.AutomationController
import com.patres.automation.gui.controller.model.SchemaGroupController
import com.patres.automation.validation.Validationable


enum class ActionBootSchema(
        val bundleName: String,
        val validation: Validationable? = null,
        private val controllerType: ActionBootControllerType
) : ActionBootable {

    ADD_GROUP("robot.action.addGroup", null, ActionBootControllerType.SCHEMA_GROUP);

    override fun validation(): Validationable? {
        return this.validation
    }

    override fun bundleName(): String {
        return this.bundleName
    }

    override fun controllerType(): ActionBootControllerType {
        return this.controllerType
    }

    override fun createController(): () -> AutomationController<*> = { SchemaGroupController() }

}
