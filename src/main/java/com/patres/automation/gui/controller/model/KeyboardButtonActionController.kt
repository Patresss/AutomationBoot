package com.patres.automation.gui.controller.model

import com.patres.automation.action.AbstractAction
import com.patres.automation.gui.custom.KeyboardField
import com.patres.automation.mapper.KeyboardFieldActionMapper
import com.patres.automation.mapper.model.KeyboardFieldActionSerialized
import com.patres.automation.type.ActionBootKeyboard
import javafx.beans.InvalidationListener
import javafx.fxml.FXML
import javafx.scene.Node

class KeyboardButtonActionController(
        action: ActionBootKeyboard
) : LabelActionController<ActionBootKeyboard>("KeyboardFieldAction.fxml", action) {

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
        return KeyboardFieldActionMapper.controllerToModel(this)
    }

    override fun toSerialized(): KeyboardFieldActionSerialized {
        return KeyboardFieldActionMapper.controllerToSerialized(this)
    }

}
