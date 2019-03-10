package com.patres.automation.validation

import com.patres.automation.gui.controller.model.TextActionController
import javafx.scene.control.Label
import javafx.scene.control.TextInputControl
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

}
