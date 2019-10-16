package com.patres.automation.gui.controller.model

import com.patres.automation.action.AbstractAction
import com.patres.automation.gui.custom.KeyboardField
import com.patres.automation.model.RootSchemaGroupModel
import com.patres.automation.type.ActionBootKeyboard
import javafx.beans.InvalidationListener
import javafx.fxml.FXML
import javafx.scene.Node

class KeyboardButtonActionController(
        root: RootSchemaGroupModel,
        parent: SchemaGroupController,
        action: ActionBootKeyboard
) : LabelActionController<ActionBootKeyboard>("KeyboardFieldAction.fxml", root, parent, action) {

    @FXML
    lateinit var keyboardField: KeyboardField

    @FXML
    override fun initialize() {
        super.initialize()
    }

    init {
        keyboardField.keys.addListener(InvalidationListener { root?.changeDetect() })
    }

    override fun getNodesToSelect(): List<Node> = super.getNodesToSelect() + listOf(keyboardField)

    override fun toModel(): AbstractAction {
        return action.createModel().invoke(keyboardField)
    }

}
