package com.patres.automation.validation

import com.patres.automation.Main
import com.patres.automation.excpetion.IntegerFormatException
import com.patres.automation.gui.controller.model.TextActionController
import com.patres.automation.util.setIntegerFilter

class IntegerValidation(controller: TextActionController) : AbstractValidation(controller) {

    companion object {
        private val invalidMessage = Main.bundle.getString("error.mustBeNumber")
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

    override fun throwException() {
        throw IntegerFormatException(validationTextField.text)
    }

}
