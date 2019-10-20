package com.patres.automation.action.script

import com.patres.automation.action.AbstractAction
import com.patres.automation.type.ActionBootBrowser
import java.awt.Desktop
import java.io.File

class OpenFileAction(path: String) : OpenFileOrDirectoryAction(path, ActionBootBrowser.OPEN_FILE)
class OpenDirectoryAction(path: String) : OpenFileOrDirectoryAction(path, ActionBootBrowser.OPEN_DIRECTORY)

abstract class OpenFileOrDirectoryAction(
        val path: String,
        action: ActionBootBrowser
) : AbstractAction(action) {

    override fun runAction() {
        val file = File(path)
        val desktop = Desktop.getDesktop()
        desktop.open(file)
    }

    override fun validate() {
        actionBoot.validation()?.check(path)
    }

}