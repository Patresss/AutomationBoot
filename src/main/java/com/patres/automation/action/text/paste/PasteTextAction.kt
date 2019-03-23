package com.patres.automation.action.text.paste

import com.patres.automation.action.TextActionModel
import com.patres.automation.gui.controller.model.TextActionController
import com.patres.automation.model.RootSchemaGroupModel
import com.patres.automation.model.SchemaGroupModel
import java.awt.Toolkit
import java.awt.datatransfer.StringSelection
import java.awt.event.KeyEvent


abstract class PasteTextAction<ControllerType : TextActionController>(
        root: RootSchemaGroupModel,
        parent: SchemaGroupModel
) : TextActionModel<ControllerType>(root, parent) {

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