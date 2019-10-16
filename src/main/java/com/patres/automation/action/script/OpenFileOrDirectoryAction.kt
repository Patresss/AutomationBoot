package com.patres.automation.action.script

import com.patres.automation.action.AbstractAction
import com.patres.automation.type.ActionBootBrowser
import java.awt.Desktop
import java.io.File


class OpenFileOrDirectoryAction(
        val path: String
) : AbstractAction(ActionBootBrowser.OPEN_FILE_OR_DIRECTORY) {

    override fun runAction() {
        val file = File(path)
        val desktop = Desktop.getDesktop()
        desktop.open(file)
    }

    override fun validate() {
        actionBoot.validation()?.check(path)
    }

}