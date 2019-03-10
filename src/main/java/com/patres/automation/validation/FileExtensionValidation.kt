package com.patres.automation.validation

import com.patres.automation.Main
import com.patres.automation.excpetion.FileHasWrongExtensiontException
import com.patres.automation.excpetion.FileNotExistException
import com.patres.automation.excpetion.IntegerFormatException
import com.patres.automation.gui.controller.model.TextActionController
import java.io.File

class FileExtensionValidation(
        controller: TextActionController,
        private val extension: String
) : AbstractValidation(controller) {

    companion object {
        private val invalidMessage = Main.bundle.getString("error.fileHasWrongExtension")
    }

    init {
        validationLabel.text = invalidMessage
    }

    override fun isConditionFulfilled(): Boolean {
        return File(validationTextField.text).exists() && File(validationTextField.text).isFile && File(validationTextField.text).name.endsWith(extension)
    }

    override fun throwException() {
        throw FileHasWrongExtensiontException(validationTextField.text, extension)
    }

}
