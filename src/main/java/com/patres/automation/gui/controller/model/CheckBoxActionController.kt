package com.patres.automation.gui.controller.model

import com.jfoenix.controls.JFXCheckBox
import com.patres.automation.type.ActionBootCheckBox
import javafx.fxml.FXML
import javafx.scene.control.Label

class CheckBoxActionController(
        action: ActionBootCheckBox,
        fxmlFile: String = "CheckBoxAction.fxml"
) : AutomationController<ActionBootCheckBox>(fxmlFile, action) {


    @FXML
    lateinit var checkBox: JFXCheckBox

    @FXML
    lateinit var validLabel: Label

    @FXML
    override fun initialize() {
        super.initialize()
        checkBox.selectedProperty().addListener { _, _, _ ->
            root?.changeDetect()
            checkUiValidation()
        }
        checkUiValidation()
        actionBoot.validation()?.getErrorMessageStringBinding()?.let {
            validLabel.textProperty().bind(it)
        }
    }

    override fun shouldCheckUiValidation() = true

    override fun checkUiValidation() {
        val valid = actionBoot.validation()?.isValid(value) ?: true || !shouldCheckUiValidation()
        actionBoot.validation()?.setStyles(!valid, validLabel, listOf(checkBox))
    }

    override fun checkValidation() {
        actionBoot.validation()?.check(value)
    }

    val value: String
        get() = checkBox.isSelected.toString()


}