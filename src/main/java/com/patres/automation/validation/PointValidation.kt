package com.patres.automation.validation

import com.patres.automation.gui.controller.model.TextActionController

class PointValidation(controller: TextActionController) : AbstractValidation(controller) {


    companion object {
        private val invalidMessage = "Point format: (x;y) "
        private val pattern = "\\((\\d+);(\\d+)\\)" // (12;34)
    }

    init {
        validationLabel.text = invalidMessage
    }

    override fun isConditionFulfilled(): Boolean {
        return validationTextField.text.matches(pattern.toRegex())
    }


}

