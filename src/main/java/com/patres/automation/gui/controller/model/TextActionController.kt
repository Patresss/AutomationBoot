package com.patres.automation.gui.controller.model

import com.patres.automation.model.AutomationModel
import com.patres.automation.validation.AbstractValidation
import javafx.fxml.FXML
import javafx.scene.Node
import javafx.scene.control.Label
import javafx.scene.control.TextInputControl

open class TextActionController(
        model: AutomationModel<out TextActionController>,
        fxmlFile: String
) : LabelActionController(model, fxmlFile) {

    @FXML
    lateinit var valueText: TextInputControl

    @FXML
    lateinit var validLabel: Label

    override fun getNodesToSelect(): List<Node> = super.getNodesToSelect() + listOf(valueText)

    var value: String
        get() = valueText.text
        set(value) {
            valueText.text = value
        }

}
