package com.patres.automation.validation

import javafx.scene.control.Label
import javafx.scene.control.TextInputControl
import java.io.File

class FileValidation(
        validationLabel: Label,
        validationField: TextInputControl
) : AbstractValidation(validationLabel, validationField) {

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
