package com.patres.automation.action.text

import com.patres.automation.action.AbstractAction
import com.patres.automation.gui.controller.box.AbstractBox
import com.patres.automation.gui.controller.model.AutomationController
import com.patres.automation.type.ActionBootBrowser
import com.patres.automation.type.ActionBootTextArea
import com.patres.automation.type.ActionBootable
import org.slf4j.LoggerFactory
import java.awt.Toolkit
import java.awt.datatransfer.StringSelection
import java.awt.event.KeyEvent
import java.io.File


class PasteTextFromFieldAction(text: String, box: AbstractBox<*>?) : PasteTextAction(ActionBootTextArea.PASTE_TEXT, text, box) {
    override fun getText() = value
}

class PasteTextFromFileAction(path: String, box: AbstractBox<*>?) : PasteTextAction(ActionBootBrowser.PASTE_TEXT_FROM_FILE, path, box) {
    override fun getText() = File(value).readText()
}

abstract class PasteTextAction(
        actionBoot: ActionBootable,
        val value: String,
        box: AbstractBox<*>?
) : AbstractAction(actionBoot, box) {

    companion object {
        private val logger = LoggerFactory.getLogger(PasteTextAction::class.java)
    }

    override fun runAction() {
        val stringSelection = StringSelection(getText())
        val clipboard = Toolkit.getDefaultToolkit().systemClipboard

        try {
            clipboard.setContents(stringSelection, stringSelection)
        } catch (e: IllegalStateException) {
            // Clipboard currently unavailable. On some platforms, the system clipboard is unavailable while it is accessed by another application.
            logger.warn("Cannot load clipboard, trying again... ")
            Thread.sleep(15)
            clipboard.setContents(stringSelection, stringSelection)
        }
        paste()
    }

    private fun paste() {
        robot.keyPress(KeyEvent.VK_CONTROL)
        robot.keyPress(KeyEvent.VK_V)
        robot.keyRelease(KeyEvent.VK_V)
        robot.keyRelease(KeyEvent.VK_CONTROL)
    }

    abstract fun getText(): String

    override fun validate() {
        actionBootType.validation()?.check(value)
    }

    override fun toStringLog() = "Action: `$actionBootType` | value: `$value`"

}