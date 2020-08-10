package com.patres.automation.validation

import com.patres.automation.excpetion.FileHasWrongExtensionException
import com.patres.automation.file.FileType
import com.patres.automation.settings.LanguageManager
import java.io.File

class FileExtensionValidation(fileType: FileType) : Validationable() {

    private val extension = fileType.extension

    override fun isValidBySpecificValidator(value: String): Boolean {
        return File(value).exists() && File(value).isFile && File(value).name.endsWith(extension)
    }

    override fun throwException(value: String) {
        throw FileHasWrongExtensionException(value, extension)
    }

    override fun getErrorMessageStringBinding(textValue: String?) = LanguageManager.createStringBinding("error.fileHasWrongExtension")

}