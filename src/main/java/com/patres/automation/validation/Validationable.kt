package com.patres.automation.validation

import javafx.beans.binding.StringBinding
import javafx.scene.Node

abstract class Validationable {

    companion object {
        const val ERROR_STYLE = "error"
    }

    abstract fun isValid(value: String): Boolean
    abstract fun throwException(value: String)

    fun check(value: String) {
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

    abstract fun getErrorMessageStringBinding(): StringBinding

}
