package com.patres.automation.type

import com.patres.automation.gui.controller.model.AutomationController
import com.patres.automation.gui.controller.model.ComboBoxController
import com.patres.automation.gui.controller.model.TextAreaActionController
import com.patres.automation.validation.Validationable


enum class ActionBootComboBox(
        val bundleName: String,
        val valuesToChoose: List<String>,
        val validation: Validationable? = null
) : ActionBootable {

    CHOOSE_LANGUAGE("robot.action.keyboard.paste", listOf("Polski", "Angielski"));

    override fun bundleName(): String {
        return this.bundleName
    }

    override fun createController(): () -> AutomationController<*> = { ComboBoxController(this) }

    override fun validation(): Validationable? {
        return this.validation
    }

}
