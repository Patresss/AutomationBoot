package com.patres.automation.validation

import com.patres.automation.gui.controller.model.TextActionController

class IntegerValidation(controller: TextActionController) : AbstractValidation(controller) {

    companion object {
        private val invalidMessage = "Only integer"
    }

    init {
        validationLabel.text = invalidMessage
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
