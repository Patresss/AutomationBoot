package com.patres.automation.type

import com.patres.automation.gui.controller.model.AutomationController
import com.patres.automation.gui.controller.model.DelayActionController
import com.patres.automation.validation.PositiveIntegerValidation
import com.patres.automation.validation.Validationable


enum class ActionBootDelay(
        val bundleName: String,
        val validation: Validationable? = null
) : ActionBootable {

    DELAY("robot.action.delay", PositiveIntegerValidation());

    override fun validation(): Validationable? {
        return this.validation
    }

    override fun bundleName(): String {
        return this.bundleName
    }

    override fun createController(): () -> AutomationController<*> = { DelayActionController(this) }

}
