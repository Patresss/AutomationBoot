package com.patres.automation.type

import com.patres.automation.gui.controller.box.AbstractBox
import com.patres.automation.gui.controller.box.ActionBox
import com.patres.automation.gui.controller.model.TimeActionController
import com.patres.automation.validation.PositiveIntegerValidation
import com.patres.automation.validation.Validationable

enum class ActionBootTime(
        val bundleName: String,
        val validation: Validationable? = null
) : ActionBootable {

    ADDITIONAL_DELAY_BETWEEN_ACTIONS("settings.additionalDelayBetweenActions", PositiveIntegerValidation()),
    DELAY("robot.action.delay", PositiveIntegerValidation());


    override fun validation(): Validationable? {
        return this.validation
    }

    override fun bundleName(): String {
        return this.bundleName
    }

    override fun createNewActionBox(): () -> AbstractBox<*> = { ActionBox(TimeActionController(this)) }

}

