package com.patres.automation.validation

import com.patres.automation.gui.controller.model.TextActionController
import com.patres.automation.util.setIntegerFilter

class IntegerValidation(controller: TextActionController) : AbstractValidation(controller) {

    companion object {
        private const val invalidMessage = "Only integer"
    }

    init {
        validationLabel.text = invalidMessage
        validationTextField.setIntegerFilter()
    }

    override fun isConditionFulfilled(): Boolean {
        try {
            Integer.parseInt(validationTextField.text)
        } catch (e: NumberFormatException) {
            return false
        } catch (e: NullPointerException) {
            return false
        }
        return true
    }

}
