package com.patres.automation.action.text

import com.patres.automation.action.AbstractAction
import com.patres.automation.gui.controller.box.AbstractBox
import com.patres.automation.gui.controller.model.AutomationController
import com.patres.automation.keyboard.KeyLoader
import com.patres.automation.type.ActionBootBrowser
import com.patres.automation.type.ActionBootTextArea
import com.patres.automation.type.ActionBootable
import javafx.beans.property.BooleanProperty
import org.slf4j.LoggerFactory
import java.io.File


class TypeTextFromFieldAction(private val text: String, automationRunningProperty: BooleanProperty, box: AbstractBox<*>?) : TypeTextAction(automationRunningProperty, ActionBootTextArea.TYPE_TEXT, box) {
    override fun getText() = text

    override fun toStringLog() = "Action: `$actionBootType` | text: `$text`"

}

class TypeTextFromFileAction(private val path: String, automationRunningProperty: BooleanProperty, box: AbstractBox<*>?) : TypeTextAction(automationRunningProperty, ActionBootBrowser.TYPE_TEXT_FROM_FILE, box) {
    override fun getText(): String {
        return File(path).readText()
    }

    override fun toStringLog() = "Action: `$actionBootType` | text: `$path`"

}

abstract class TypeTextAction(
        private val automationRunningProperty: BooleanProperty,
        actionBoot: ActionBootable,
        box: AbstractBox<*>?
) : AbstractAction(actionBoot, box) {

    companion object {
        private val LOGGER = LoggerFactory.getLogger(TypeTextAction::class.java)
    }

    abstract fun getText(): String

    override fun runAction() {
        run loop@{
            getText().toCharArray()
                    .forEach {
                        if (!automationRunningProperty.get()) {
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