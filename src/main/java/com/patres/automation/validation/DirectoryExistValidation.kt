package com.patres.automation.validation

import com.patres.automation.excpetion.DirectoryNotExistException
import java.io.File

class DirectoryExistValidation : Validationable() {

    override fun isValid(value: String): Boolean {
        val file = File(value)
        return file.exists() && file.isDirectory
    }

    override fun throwException(value: String) {
        throw DirectoryNotExistException(value)
    }

    override fun getErrorMessageProperty() = "error.directoryDoesntExist"

}
