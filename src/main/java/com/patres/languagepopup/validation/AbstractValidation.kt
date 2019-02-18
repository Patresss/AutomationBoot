package com.patres.languagepopup.validation

import javafx.scene.Node
import javafx.scene.control.Label
import javafx.scene.control.TextField

abstract class AbstractValidation(
        private var validationLabel: Label,
        protected var validationTextField: TextField
) {

    companion object {
        const val ERROR_STYLE = "error"
    }

    abstract val isConditionFulfilled: Boolean

    private fun setError(isError: Boolean) {
        validationLabel.isVisible = isError
        setStyle(validationLabel, isError)
        setStyle(validationTextField, isError)
    }

    private fun setStyle(node: Node, setStyle: Boolean) {
        node.styleClass.remove(ERROR_STYLE)
        if (setStyle && !node.styleClass.contains(ERROR_STYLE)) {
            node.styleClass.add(ERROR_STYLE)
        }
    }

    private fun activateLabelValidate() {
        val isEmpty = validationTextField.text == ""
        setError(!isConditionFulfilled && !isEmpty)
    }

    fun activateControlListener() {
        activateLabelValidate()
        validationTextField.textProperty().addListener { _, _, _ -> activateLabelValidate() }

    }


}
