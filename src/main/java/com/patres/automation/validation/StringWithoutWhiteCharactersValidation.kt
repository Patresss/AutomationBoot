package com.patres.automation.validation

import com.patres.automation.excpetion.StringWithWhiteCharactersException

class StringWithoutWhiteCharactersValidation : Validationable() {

    override fun isValid(value: String): Boolean {
        return value.toCharArray().none { it.isWhitespace() }
    }

    override fun throwException(value: String) {
        throw StringWithWhiteCharactersException(value)
    }

    override fun getErrorMessageProperty() = "error.stringWithWhiteCharacters"

}
