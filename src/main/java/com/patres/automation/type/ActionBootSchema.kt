package com.patres.automation.type

import com.patres.automation.gui.controller.box.AbstractBox
import com.patres.automation.gui.controller.box.SchemaGroupController
import com.patres.automation.validation.Validationable


enum class ActionBootSchema(
        val bundleName: String,
        val validation: Validationable? = null
) : ActionBootable {

    SCHEMA_GROUP("robot.action.addGroup", null);

    override fun validation(): Validationable? {
        return this.validation
    }

    override fun bundleName(): String {
        return this.bundleName
    }

    override fun createActinBox(): () -> AbstractBox<*> = { SchemaGroupController() }

}
