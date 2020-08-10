package com.patres.automation.validation

import com.patres.automation.parameter.received.ReceivedParameterConverter
import javafx.beans.binding.StringBinding
import javafx.scene.Node

abstract class Validationable {

    companion object {
        const val ERROR_STYLE = "error"
    }

    fun isValid(value: String): Boolean {
        if (ReceivedParameterConverter.PARAMETER_PATTERN.containsMatchIn(value)) {
            return true
        }
        return isValidBySpecificValidator(value)
    }

    open fun check(value: String) {
        if (!isValid(value)) {
            throwException(value)
        }
    }

    fun setStyles(isError: Boolean, validationLabel: Node, components: List<Node>) {
        validationLabel.isVisible = isError
        components.forEach { setStyle(it, isError) }
    }

    private fun setStyle(node: Node, setStyle: Boolean) {
        node.styleClass.remove(ERROR_STYLE)
        if (setStyle && !node.styleClass.contains(ERROR_STYLE)) {
            node.styleClass.add(ERROR_STYLE)
        }
    }

    abstract fun getErrorMessageStringBinding(textValue: String? = null): StringBinding
    abstract fun isValidBySpecificValidator(value: String): Boolean
    abstract fun throwException(value: String)

}
