package com.patres.automation.validation

import com.patres.automation.excpetion.StringWithWhiteCharactersException
import com.patres.automation.settings.LanguageManager

class StringWithoutWhiteCharactersValidation : Validationable() {

    override fun isValidBySpecificValidator(value: String): Boolean {
        return value.toCharArray().none { it.isWhitespace() }
    }

    override fun throwException(value: String) {
        throw StringWithWhiteCharactersException(value)
    }

    override fun getErrorMessageStringBinding(textValue: String?) = LanguageManager.createStringBinding("error.stringWithWhiteCharacters")

}
