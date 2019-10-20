package com.patres.automation.type

import com.patres.automation.gui.controller.model.AutomationController
import com.patres.automation.gui.controller.model.SchemaGroupController
import com.patres.automation.validation.Validationable


enum class ActionBootSchema(
        val bundleName: String,
        val validation: Validationable? = null
) : ActionBootable {

    ADD_GROUP("robot.action.addGroup", null);

    override fun validation(): Validationable? {
        return this.validation
    }

    override fun bundleName(): String {
        return this.bundleName
    }

    override fun createController(): () -> AutomationController<*> = { SchemaGroupController() }

}
