package com.patres.automation.type

import com.patres.automation.gui.controller.box.AbstractBox
import com.patres.automation.gui.controller.box.ActionBox
import com.patres.automation.gui.controller.model.PasswordFieldActionController
import com.patres.automation.validation.Validationable


enum class ActionBootPasswordField(
        val bundleName: String,
        val validation: Validationable? = null
) : ActionBootable {

    SERVER_PASSWORD("settings.password", null);


    override fun validation(): Validationable? {
        return this.validation
    }

    override fun bundleName(): String {
        return this.bundleName
    }

    override fun createActionBox(): () -> AbstractBox<*> = { ActionBox(PasswordFieldActionController(this)) }

}
