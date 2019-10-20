package com.patres.automation.type

import com.patres.automation.gui.controller.model.AutomationController
import com.patres.automation.gui.controller.model.TextAreaActionController
import com.patres.automation.validation.Validationable


enum class ActionBootTextArea(
        val bundleName: String,
        val validation: Validationable? = null
) : ActionBootable {

    PASTE_TEXT("robot.action.keyboard.paste", null),
    TYPE_TEXT("robot.action.keyboard.type", null);

    override fun validation(): Validationable? {
        return this.validation
    }

    override fun bundleName(): String {
        return this.bundleName
    }

    override fun createController(): () -> AutomationController<*> = { TextAreaActionController(this) }

}
