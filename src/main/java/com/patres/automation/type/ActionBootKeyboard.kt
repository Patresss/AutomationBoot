package com.patres.automation.type

import com.patres.automation.gui.controller.model.AutomationController
import com.patres.automation.gui.controller.model.KeyboardButtonActionController
import com.patres.automation.validation.Validationable


enum class ActionBootKeyboard(
        val bundleName: String,
        val validation: Validationable? = null
) : ActionBootable {

    PRESS_KEYBOARD_BUTTON("robot.action.keyboard.press");

    override fun validation(): Validationable? {
        return this.validation
    }

    override fun bundleName(): String {
        return this.bundleName
    }

    override fun createController(): () -> AutomationController<*> = { KeyboardButtonActionController(this) }

}
