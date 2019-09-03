package com.patres.automation.validation

import com.patres.automation.Main
import com.patres.automation.excpetion.PointVectorFormatException
import com.patres.automation.gui.controller.model.MousePointActionController
import com.patres.automation.gui.controller.model.TextActionController

class PointVectorValidation(val controller: MousePointActionController) : AbstractValidation(controller) {

    companion object {
        private val invalidMessage = Main.bundle.getString("error.mustBePointOrVector")
        private const val pointPattern = "\\((\\d+);(\\d+)\\)" // (12;34)
        private const val vectorPattern = "V?\\((-?\\d+);(-?\\d+)\\)" // V(-12;34)
    }

    init {
        validationLabel.text = invalidMessage
    }

    override fun isValid(): Boolean {
        return !controller.valueText.isVisible || (validationTextField.text.matches(pointPattern.toRegex()) || validationTextField.text.matches(vectorPattern.toRegex()))
    }

    override fun throwException() {
        throw PointVectorFormatException(validationTextField.text)
    }

}

