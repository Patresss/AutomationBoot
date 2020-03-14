package com.patres.automation.validation

import com.patres.automation.excpetion.IntegerFormatException
import com.patres.automation.settings.LanguageManager

class IntegerValidation : Validationable() {

    override fun isValid(value: String): Boolean {
        try {
            Integer.parseInt(value)
        } catch (e: NumberFormatException) {
            return false
        } catch (e: NullPointerException) {
            return false
        }
        return true
    }

    override fun throwException(value: String) {
        throw IntegerFormatException(value)
    }

    override fun getErrorMessageStringBinding() = LanguageManager.createStringBinding("error.mustBeNumber")

}
