package com.patres.automation.action.script

import com.patres.automation.action.AbstractAction
import com.patres.automation.gui.controller.box.AbstractBox
import com.patres.automation.gui.controller.model.AutomationController
import com.patres.automation.type.ActionBootBrowser
import java.io.File

class WindowsRunAndWaitScriptAction(path: String, box: AbstractBox<*>?) : WindowsScriptAction(path, true, ActionBootBrowser.WINDOWS_SCRIPT_RUN_AND_WAITE, box)
class WindowsRunScriptAction(path: String, box: AbstractBox<*>?) : WindowsScriptAction(path, false, ActionBootBrowser.WINDOWS_SCRIPT_RUN, box)

abstract class WindowsScriptAction(
        val path: String,
        private val wait: Boolean,
        actionBoot: ActionBootBrowser,
        box: AbstractBox<*>?
) : AbstractAction(actionBoot, box) {

    override fun runAction() {
        val file = File(path)

        val processBuilder = ProcessBuilder().apply {
            command(file.absolutePath)
        }

        if (wait) {
            val process = processBuilder.start()
            process.waitFor()
        }
    }

    override fun validate() {
        actionBootType.validation()?.check(path)
    }

    override fun toStringLog() = "Action: `$actionBootType` | path: `$path`, wait: `$wait`"

}