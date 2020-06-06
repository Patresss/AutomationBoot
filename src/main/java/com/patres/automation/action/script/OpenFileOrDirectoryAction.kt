package com.patres.automation.action.script

import com.patres.automation.action.AbstractAction
import com.patres.automation.gui.controller.box.AbstractBox
import com.patres.automation.gui.controller.model.AutomationController
import com.patres.automation.type.ActionBootBrowser
import java.awt.Desktop
import java.io.File

class OpenFileAction(path: String, box: AbstractBox<*>?) : OpenFileOrDirectoryAction(path, ActionBootBrowser.OPEN_FILE, box)
class OpenDirectoryAction(path: String, box: AbstractBox<*>?) : OpenFileOrDirectoryAction(path, ActionBootBrowser.OPEN_DIRECTORY, box)

abstract class OpenFileOrDirectoryAction(
        val path: String,
        action: ActionBootBrowser,
        box: AbstractBox<*>?
) : AbstractAction(action, box) {

    override fun runAction() {
        val file = File(path)
        val desktop = Desktop.getDesktop()
        desktop.open(file)
    }

    override fun validate() {
        actionBootType.validation()?.check(path)
    }

    override fun toStringLog() = "Action: `$actionBootType` | path: `$path`"

}