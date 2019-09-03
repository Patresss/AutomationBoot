package com.patres.automation.validation

import com.patres.automation.gui.controller.model.TextActionController
import javafx.scene.Node
import javafx.scene.control.Label
import javafx.scene.control.TextInputControl

abstract class AbstractValidation(controller: TextActionController) {

    companion object {
        const val ERROR_STYLE = "error"
    }

    protected var validationLabel: Label = controller.validLabel
    protected var validationTextField: TextInputControl = controller.valueText

    abstract fun isValid(): Boolean

    private fun setError(isError: Boolean) {
        validationLabel.isVisible = isError
        setStyle(validationTextField, isError)
    }

    private fun setStyle(node: Node, setStyle: Boolean) {
        node.styleClass.remove(ERROR_STYLE)
        if (setStyle && !node.styleClass.contains(ERROR_STYLE)) {
            node.styleClass.add(ERROR_STYLE)
        }
    }

    private fun activateLabelValidate() {
        setError(!isValid() && validationTextField.text.isNotEmpty())
    }

    fun activateControlListener() {
        activateLabelValidate()
        validationTextField.textProperty().addListener { _, _, _ -> activateLabelValidate() }
        validationTextField.visibleProperty().addListener { _, _, _ -> activateLabelValidate() }
    }

    abstract fun throwException()

}
