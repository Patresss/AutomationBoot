package com.patres.automation.validation

import com.patres.automation.excpetion.NumberMustBeBetweenException
import com.patres.automation.server.ServerBoot
import com.patres.automation.settings.LanguageManager

class PortValidation : Validationable() {

    override fun isValid(value: String): Boolean {
        return try {
            val parseInt = Integer.parseInt(value)
            parseInt in ServerBoot.MIN_PORT..ServerBoot.MAX_PORT
        } catch (e: NumberFormatException) {
            false
        } catch (e: NullPointerException) {
            false
        }
    }

    override fun throwException(value: String) {
        throw NumberMustBeBetweenException(value)
    }

    override fun getErrorMessageStringBinding() = LanguageManager.createStringBinding("error.mustBeNumberBetween", ServerBoot.MIN_PORT, ServerBoot.MAX_PORT)

}
