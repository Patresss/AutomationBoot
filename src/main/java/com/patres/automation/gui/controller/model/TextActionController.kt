package com.patres.automation.gui.controller.model

import com.patres.automation.parameter.received.ReceivedParameterConverter
import com.patres.automation.parameter.sent.SentParameter
import com.patres.automation.type.ActionBootable
import javafx.fxml.FXML
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
            changeErrorMessage()
            root?.changeDetect()
            checkUiValidation()
        }
        checkUiValidation()
    }

    override fun shouldCheckUiValidation() = value.isNotEmpty()

    override fun checkUiValidation() {
        val valid = actionBoot.validation()?.isValid(value) ?: true || !shouldCheckUiValidation()
        actionBoot.validation()?.setStyles(!valid, validLabel, listOf(valueText))
    }

    override fun checkValidation() {
        actionBoot.validation()?.check(value)
    }

    override fun changeErrorMessage() {
        actionBoot.validation()?.getErrorMessageStringBinding(value)?.let {
            validLabel.textProperty().bind(it)
        }
    }

    var value: String
        get() = valueText.text
        set(value) {
            valueText.text = value
        }

    fun calculateParametrizedValue(sentParameters: Set<SentParameter>): String {
        return ReceivedParameterConverter(value, sentParameters).replaceValue()
    }

}
