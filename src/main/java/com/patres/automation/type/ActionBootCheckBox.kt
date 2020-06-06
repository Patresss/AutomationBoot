package com.patres.automation.type

import com.patres.automation.gui.controller.box.AbstractBox
import com.patres.automation.gui.controller.box.ActionBox
import com.patres.automation.gui.controller.model.CheckBoxActionController
import com.patres.automation.validation.Validationable


enum class ActionBootCheckBox(
        val bundleName: String,
        val validation: Validationable? = null
) : ActionBootable {

    ENABLE_REST("settings.enableRest", null),
    ENABLE_AUTHENTICATOR("settings.enableAuthenticator", null),
    GO_TO_POINT_SELECTION_WHEN_NEW_MOUSE_ACTION_IS_ADDED("settings.gotToPointSelection", null);

    override fun validation(): Validationable? {
        return this.validation
    }

    override fun bundleName(): String {
        return this.bundleName
    }

    override fun createNewActionBox(): () -> AbstractBox<*> = { ActionBox(CheckBoxActionController(this)) }

}
