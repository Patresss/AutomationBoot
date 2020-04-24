package com.patres.automation.type

import com.patres.automation.gui.controller.box.AbstractBox
import com.patres.automation.gui.controller.box.ActionBox
import com.patres.automation.gui.controller.model.CheckBoxActionController
import com.patres.automation.validation.Validationable


enum class ActionBootCheckBox(
        val bundleName: String,
        val validation: Validationable? = null
) : ActionBootable {

    ENABLE_REST("settings.enableRest", null);


    override fun validation(): Validationable? {
        return this.validation
    }

    override fun bundleName(): String {
        return this.bundleName
    }

    override fun createActinBox(): () -> AbstractBox<*> = { ActionBox(CheckBoxActionController(this)) }

}
