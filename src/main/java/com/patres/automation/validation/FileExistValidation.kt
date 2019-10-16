package com.patres.automation.validation

import com.patres.automation.excpetion.FileNotExistException
import java.io.File

class FileExistValidation : Validationable() {

    override fun isValid(value: String): Boolean {
        return File(value).exists() && !File(value).isDirectory
    }

    override fun throwException(value: String) {
        throw FileNotExistException(value)
    }

    override fun getErrorMessageProperty() = "error.fileDoesntExist"

}
