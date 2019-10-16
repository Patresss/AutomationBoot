package com.patres.automation.validation

import com.patres.automation.file.FileType
import com.patres.automation.excpetion.FileHasWrongExtensiontException
import java.io.File

class FileExtensionValidation(fileType: FileType) : Validationable() {

    private val extension = fileType.extension

    override fun isValid(value: String): Boolean {
        return File(value).exists() && File(value).isFile && File(value).name.endsWith(extension)
    }

    override fun throwException(value: String) {
        throw FileHasWrongExtensiontException(value, extension)
    }

    override fun getErrorMessageProperty() = "error.fileHasWrongExtension"

}