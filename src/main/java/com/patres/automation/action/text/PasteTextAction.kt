package com.patres.automation.action.text

import com.patres.automation.action.AbstractAction
import com.patres.automation.type.ActionBootBrowser
import com.patres.automation.type.ActionBootTextArea
import com.patres.automation.type.ActionBootable
import java.awt.Toolkit
import java.awt.datatransfer.StringSelection
import java.awt.event.KeyEvent
import java.io.File


class PasteTextFromFieldAction(text: String) : PasteTextAction(ActionBootTextArea.PASTE_TEXT, text) {
    override fun getText() = value
}

class PasteTextFromFileAction(path: String) : PasteTextAction(ActionBootBrowser.PASTE_TEXT_FROM_FILE, path) {
    override fun getText() = File(value).readText()
}

abstract class PasteTextAction(
        actionBoot: ActionBootable,
        val value: String
) : AbstractAction(actionBoot) {

    override fun runAction() {
        val stringSelection = StringSelection(getText())
        val clipboard = Toolkit.getDefaultToolkit().systemClipboard
        clipboard.setContents(stringSelection, stringSelection)
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
        actionBoot.validation()?.check(value)
    }

}