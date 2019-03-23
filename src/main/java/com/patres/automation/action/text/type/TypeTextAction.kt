package com.patres.automation.action.text.type

import com.patres.automation.action.TextActionModel
import com.patres.automation.gui.controller.model.TextActionController
import com.patres.automation.keyboard.KeyLoader
import com.patres.automation.model.RootSchemaGroupModel
import com.patres.automation.model.SchemaGroupModel
import org.slf4j.LoggerFactory


abstract class TypeTextAction<ControllerType : TextActionController>(
        root: RootSchemaGroupModel,
        parent: SchemaGroupModel
) : TextActionModel<ControllerType>(root, parent) {

    companion object {
        private val LOGGER = LoggerFactory.getLogger(TypeTextAction::class.java)
    }

    abstract fun getText(): String

    override fun runAction() {
        run loop@{
            getText().toCharArray()
                    .forEach {
                        if (root.rootSchemaKeyListener.isStop) {
                            return@loop
                        }
                        pressKey(it)
                    }
        }
    }

    private fun pressKey(character: Char) {
        val keyList = KeyLoader.getKey(character)
        keyList.forEach {
            try {
                robot.keyPress(it)
            } catch (e: IllegalArgumentException) {
                LOGGER.warn("Invalid key character: $character")
            }
        }

        keyList.reversed().forEach {
            try {
                robot.keyRelease(it)
            } catch (e: IllegalArgumentException) {
                LOGGER.warn("Invalid key character: $character")
            }
        }
    }

}