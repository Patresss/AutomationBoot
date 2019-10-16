package com.patres.automation.validation

import com.patres.automation.excpetion.FileNotExistException
import java.io.File

class FileOrDirectoryExistValidation : Validationable() {

    override fun isValid(value: String): Boolean {
        return File(value).exists()
    }

    override fun throwException(value: String) {
        throw FileNotExistException(value)
    }

    override fun getErrorMessageProperty() = "error.fileOrDirectoryDoesntExist"

}
