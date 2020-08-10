package com.patres.automation.validation

import com.patres.automation.excpetion.FileNotExistException
import com.patres.automation.settings.LanguageManager
import java.io.File

class FileExistValidation : Validationable() {

    override fun isValidBySpecificValidator(value: String): Boolean {
        return File(value).exists() && !File(value).isDirectory
    }

    override fun throwException(value: String) {
        throw FileNotExistException(value)
    }

    override fun getErrorMessageStringBinding(textValue: String?) = LanguageManager.createStringBinding("error.fileDoesntExist")

}
