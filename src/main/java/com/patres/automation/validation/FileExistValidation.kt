package com.patres.automation.validation

import com.patres.automation.Main
import com.patres.automation.excpetion.FileNotExistException
import com.patres.automation.gui.controller.model.TextActionController
import java.io.File

class FileExistValidation(controller: TextActionController) : AbstractValidation(controller) {

    companion object {
        private val invalidMessage = Main.bundle.getString("error.fileDoesntExist")
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
