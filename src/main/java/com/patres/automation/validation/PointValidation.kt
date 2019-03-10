package com.patres.automation.validation

import com.patres.automation.Main
import com.patres.automation.excpetion.PointFormatException
import com.patres.automation.gui.controller.model.TextActionController

class PointValidation(controller: TextActionController) : AbstractValidation(controller) {

    companion object {
        private val invalidMessage = Main.bundle.getString("error.mustBePoint")
        private const val pattern = "\\((\\d+);(\\d+)\\)" // (12;34)
    }

    init {
        validationLabel.text = invalidMessage
    }

    override fun isConditionFulfilled(): Boolean {
        return validationTextField.text.matches(pattern.toRegex())
    }

    override fun throwException() {
        throw PointFormatException(validationTextField.text)
    }

}

