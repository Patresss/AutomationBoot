package com.patres.automation.validation

import com.patres.automation.excpetion.DirectoryNotExistException
import com.patres.automation.settings.LanguageManager
import java.io.File

class DirectoryExistValidation : Validationable() {

    override fun isValidBySpecificValidator(value: String): Boolean {
        val file = File(value)
        return file.exists() && file.isDirectory
    }

    override fun throwException(value: String) {
        throw DirectoryNotExistException(value)
    }

    override fun getErrorMessageStringBinding(textValue: String?) = LanguageManager.createStringBinding("error.directoryDoesntExist")

}
