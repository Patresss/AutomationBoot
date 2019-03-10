package com.patres.automation.validation

import com.patres.automation.excpetion.FileNotExistException
import com.patres.automation.excpetion.IntegerFormatException
import com.patres.automation.gui.controller.model.TextActionController
import java.io.File

class FileValidation(controller: TextActionController) : AbstractValidation(controller) {

    companion object {
        private const val invalidMessage = "File does not exist"
    }

    init {
        validationLabel.text = invalidMessage
    }

    override fun isConditionFulfilled(): Boolean {
        return File(validationTextField.text).exists() && !File(validationTextField.text).isDirectory
    }

    override fun throwException() {
        throw FileNotExistException(validationTextField.text)
    }
}
