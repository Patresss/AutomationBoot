package com.patres.automation.validation

import com.patres.automation.excpetion.FileNotExistException
import java.io.File

class DirectoryExistValidation : Validationable() {

    override fun isValid(value: String): Boolean {
        return File(value).exists()
    }

    override fun throwException(value: String) {
        throw FileNotExistException(value)
    }

    override fun getErrorMessageProperty() = "error.fileOrDirectoryDoesntExist"

}
