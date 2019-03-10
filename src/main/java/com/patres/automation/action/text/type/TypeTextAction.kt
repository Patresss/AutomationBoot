package com.patres.automation.action.text.type

import com.patres.automation.action.TextActionModel
import com.patres.automation.gui.controller.model.TextActionController
import com.patres.automation.keyboard.GlobalKeyListener
import com.patres.automation.keyboard.KeyLoader
import com.patres.automation.model.RootSchemaGroupModel
import com.patres.automation.model.SchemaGroupModel


abstract class TypeTextAction<ControllerType : TextActionController>(
        root: RootSchemaGroupModel,
        parent: SchemaGroupModel
) : TextActionModel<ControllerType>(root, parent) {

    abstract fun getText(): String

    override fun runAction() {
        run loop@{
            getText().toCharArray()
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