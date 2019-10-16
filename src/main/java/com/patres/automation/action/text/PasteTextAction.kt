package com.patres.automation.action.text

import com.patres.automation.action.AbstractAction
import com.patres.automation.type.ActionBootBrowser
import com.patres.automation.type.ActionBootTextArea
import com.patres.automation.type.ActionBootable
import java.awt.Toolkit
import java.awt.datatransfer.StringSelection
import java.awt.event.KeyEvent
import java.io.File


class PasteTextFromFieldAction(private val text: String) : PasteTextAction(ActionBootTextArea.PASTE_TEXT) {
    override fun getText() = text

    override fun validate() {
        actionBoot.validation()?.check(text)
    }

}

class PasteTextFromFileAction(private val path: String) : PasteTextAction(ActionBootBrowser.PASTE_TEXT_FROM_FILE) {
    override fun getText() = File(path).readText()

    override fun validate() {
        actionBoot.validation()?.check(path)
    }
}

abstract class PasteTextAction(
        actionBoot: ActionBootable
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

}