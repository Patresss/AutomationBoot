package com.patres.automation.validation

import com.patres.automation.gui.controller.model.TextActionController
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.control.TextInputControl

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
