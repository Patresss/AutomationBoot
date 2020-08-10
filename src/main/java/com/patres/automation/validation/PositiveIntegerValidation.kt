package com.patres.automation.validation

import com.patres.automation.excpetion.PositiveIntegerFormatException
import com.patres.automation.settings.LanguageManager

class PositiveIntegerValidation : Validationable() {

    override fun isValidBySpecificValidator(value: String): Boolean {
        return try {
            Integer.parseInt(value) >= 0
        } catch (e: NumberFormatException) {
            false
        } catch (e: NullPointerException) {
            false
        }
    }

    override fun throwException(value: String) {
        throw PositiveIntegerFormatException(value)
    }

    override fun getErrorMessageStringBinding(textValue: String?) = LanguageManager.createStringBinding("error.mustBePositiveNumber")

}
