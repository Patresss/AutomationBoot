package com.patres.automation.type

import com.patres.automation.gui.controller.box.AbstractBox
import com.patres.automation.gui.controller.box.ActionBox
import com.patres.automation.gui.controller.model.TextFieldActionController
import com.patres.automation.validation.PortValidation
import com.patres.automation.validation.PositiveIntegerValidation
import com.patres.automation.validation.StringWithoutWhiteCharactersValidation
import com.patres.automation.validation.Validationable


enum class ActionBootTextField(
        val bundleName: String,
        val validation: Validationable? = null
) : ActionBootable {

    SCROLL_WHEEL_UP("robot.action.scrollWheel.up", PositiveIntegerValidation()),
    SCROLL_WHEEL_DOWN("robot.action.scrollWheel.down", PositiveIntegerValidation()),

    PORT("settings.port", PortValidation()),
    ENDPOINT_NAME("settings.endpointName", StringWithoutWhiteCharactersValidation()),
    SERVER_USERNAME("settings.username", StringWithoutWhiteCharactersValidation());


    override fun validation(): Validationable? {
        return this.validation
    }

    override fun bundleName(): String {
        return this.bundleName
    }

    override fun createNewActionBox(): () -> AbstractBox<*> = { ActionBox(TextFieldActionController(this)) }

}
