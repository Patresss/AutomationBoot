package com.patres.automation.action.keyboard

import com.patres.automation.gui.controller.model.KeyboardButtonActionController
import com.patres.automation.model.ActionNodeModel
import com.patres.automation.model.RootSchemaGroupModel
import com.patres.automation.model.SchemaGroupModel
import com.patres.automation.util.KeyboardKey
import com.patres.automation.util.LoaderFactory
import javafx.scene.Node


abstract class KeyboardButtonAction(
        root: RootSchemaGroupModel,
        parent: SchemaGroupModel
) : ActionNodeModel<KeyboardButtonActionController>(root, parent) {

    override val controller: KeyboardButtonActionController = LoaderFactory.createKeyboardFieldActionController(this)

    fun getActionValue() = controller.keyboardField.keys

    fun setActionValue(keys: List<KeyboardKey>) {
        keys.forEach { controller.keyboardField.addKeyboardButton(it) }
    }

    override fun getMainNode(): Node = controller.getMainNode()

    override fun getMainInsideNode(): Node = controller.getMainInsideNode()

}