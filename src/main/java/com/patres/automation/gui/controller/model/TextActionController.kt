package com.patres.automation.gui.controller.model

import com.patres.automation.Main
import com.patres.automation.type.ActionBootable
import javafx.fxml.FXML
import javafx.scene.Node
import javafx.scene.control.Label
import javafx.scene.control.TextInputControl

abstract class TextActionController<ActionBootType : ActionBootable>(
        fxmlFile: String,
        action: ActionBootType
) : AutomationController<ActionBootType>(fxmlFile, action) {

    @FXML
    lateinit var valueText: TextInputControl

    @FXML
    lateinit var validLabel: Label

    @FXML
    override fun initialize() {
        super.initialize()
        valueText.textProperty().addListener { _, _, _ ->
            root?.changeDetect()
            enableUiValidation()
        }
        enableUiValidation()
        action.validation()?.getErrorMessageProperty()?.let {
            validLabel.textProperty().bind(Main.createStringBinding(it))
        }
    }

    open fun shouldCheckValidation(): Boolean {
        return value.isNotEmpty()
    }

    fun enableUiValidation() {
        val valid = action.validation()?.isValid(value) ?: true || !shouldCheckValidation()
        action.validation()?.setStyles(!valid, validLabel, listOf(valueText))
    }

    override fun checkValidation() {
        action.validation()?.check(value)
    }

    var value: String
        get() = valueText.text
        set(value) {
            valueText.text = value
        }

}
