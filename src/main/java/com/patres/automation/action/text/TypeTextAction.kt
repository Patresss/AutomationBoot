package com.patres.automation.action.text

import com.patres.automation.action.TextFieldActionModel
import com.patres.automation.gui.controller.model.TextAreaActionController
import com.patres.automation.keyboard.GlobalKeyListener
import com.patres.automation.menuItem.MenuItem
import com.patres.automation.model.RootSchemaGroupModel
import com.patres.automation.model.SchemaGroupModel
import com.patres.automation.util.KeyLoader


class TypeTextAction(
        root: RootSchemaGroupModel,
        parent: SchemaGroupModel
) : TextFieldActionModel<TextAreaActionController>(root, parent) {

    override val controller: TextAreaActionController = TextAreaActionController(this)

    init {
        controller.actionLabel.text = MenuItem.TYPE_TEXT.actionName
    }

    override fun runAction() {
        run loop@{
            getActionValue().toCharArray()
                    .map { KeyLoader.getKey(it) }
                    .forEach {
                        if (GlobalKeyListener.isStop) {
                            return@loop
                        }
                        pressKey(it)
                    }
        }
    }

    private fun pressKey(keyCode: List<Int>) {
        keyCode.forEach { robot.keyPress(it) }
        keyCode.reversed().forEach { robot.keyRelease(it) }
    }

}