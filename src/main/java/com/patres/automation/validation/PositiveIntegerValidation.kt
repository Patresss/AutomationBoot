package com.patres.automation.validation

import com.patres.automation.excpetion.PositiveIntegerFormatException

class PositiveIntegerValidation : Validationable() {

    override fun isValid(value: String): Boolean {
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

    override fun getErrorMessageProperty() = "error.mustBePositiveNumber"

}
