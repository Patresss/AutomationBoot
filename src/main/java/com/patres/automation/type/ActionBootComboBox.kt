package com.patres.automation.type

import com.patres.automation.gui.controller.model.ComboBoxController
import com.patres.automation.settings.Language
import com.patres.automation.validation.Validationable


abstract class ActionBootComboBox(
        val bundleName: String
) : ActionBootable {

    override fun bundleName(): String {
        return this.bundleName
    }

    override fun validation(): Validationable? = null
}

// new class because I cannot create enum with generic
class ChooseLanguageActionBootComboBox() : ActionBootComboBox("settings.chooseLanguage") {
    override fun createController(): () -> ComboBoxController<Language> = { ComboBoxController(this, Language.values().toList()) }
}