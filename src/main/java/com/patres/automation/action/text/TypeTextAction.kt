package com.patres.automation.action.text

import com.patres.automation.action.TextFieldActionModel
import com.patres.automation.gui.controller.model.TextFieldActionController
import com.patres.automation.menuItem.MenuItem
import com.patres.automation.model.RootSchemaGroupModel
import com.patres.automation.model.SchemaGroupModel
import com.patres.automation.util.KeyLoader
import com.patres.automation.util.LoaderFactory


class TypeTextAction(
        root: RootSchemaGroupModel,
        parent: SchemaGroupModel
) : TextFieldActionModel(root, parent) {

    override val controller: TextFieldActionController = LoaderFactory.createTextAreaActionController(this)

    init {
        controller.actionLabel.text = MenuItem.TYPE_TEXT.actionName
    }

    override fun runAction() {
        getActionValue().toCharArray()
                .map { KeyLoader.getKey(it) }
                .forEach { pressKey(it) }
    }

    private fun pressKey(keyCode: List<Int>) {
        keyCode.forEach { robot.keyPress(it) }
        keyCode.reversed().forEach { robot.keyRelease(it) }
    }

}