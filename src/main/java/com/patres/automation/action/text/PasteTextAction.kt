package com.patres.automation.action.text

import com.patres.automation.action.TextFieldActionModel
import com.patres.automation.gui.controller.model.TextAreaActionController
import com.patres.automation.menuItem.MenuItem
import com.patres.automation.model.RootSchemaGroupModel
import com.patres.automation.model.SchemaGroupModel
import java.awt.Toolkit
import java.awt.datatransfer.StringSelection
import java.awt.event.KeyEvent


class PasteTextAction(
        root: RootSchemaGroupModel,
        parent: SchemaGroupModel
) : TextFieldActionModel<TextAreaActionController>(root, parent) {

    override val controller: TextAreaActionController = TextAreaActionController(this)

    init {
        controller.actionLabel.text = MenuItem.PASTE_TEXT.actionName
    }

    override fun runAction() {
        val stringSelection = StringSelection(controller.value)
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
}