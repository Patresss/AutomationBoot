package com.patres.automation.gui.controller.model

import com.patres.automation.Main
import com.patres.automation.model.RootSchemaGroupModel
import com.patres.automation.type.ActionBootable
import javafx.fxml.FXML
import javafx.scene.Node
import javafx.scene.control.Label
import javafx.scene.control.TextInputControl

abstract class TextActionController<ActionBootType : ActionBootable>(
        fxmlFile: String,
        root: RootSchemaGroupModel,
        parent: SchemaGroupController,
        action: ActionBootType
) : LabelActionController<ActionBootType>(fxmlFile, root, parent, action) {

    @FXML
    lateinit var valueText: TextInputControl

    @FXML
    lateinit var validLabel: Label

    override fun getNodesToSelect(): List<Node> = super.getNodesToSelect() + listOf(valueText)

    @FXML
    override fun initialize() {
        super.initialize()
        valueText.textProperty().addListener { _, _, _ ->
            root.changeDetect()
            checkValidation()
        }
        checkValidation()
        action.validation()?.getErrorMessageProperty()?.let {
            validLabel.textProperty().bind(Main.createStringBinding(it))
        }
    }

    open fun shouldCheckValidation(): Boolean {
        return value.isNotEmpty()
    }

    private fun checkValidation() {
        val valid = action.validation()?.isValid(value) ?: true || !shouldCheckValidation()
        action.validation()?.setStyles(!valid, validLabel, listOf(valueText))
    }

    var value: String
        get() = valueText.text
        set(value) {
            valueText.text = value
        }

}
