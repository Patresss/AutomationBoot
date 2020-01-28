package com.patres.automation.action.script

import com.patres.automation.action.AbstractAction
import com.patres.automation.type.ActionBootBrowser
import java.io.File

class WindowsRunAndWaitScriptAction(path: String) : WindowsScriptAction(path, true, ActionBootBrowser.WINDOWS_SCRIPT_RUN_AND_WAITE)
class WindowsRunScriptAction(path: String) : WindowsScriptAction(path, false, ActionBootBrowser.WINDOWS_SCRIPT_RUN_AND_WAITE)

abstract class WindowsScriptAction(
        val path: String,
        private val wait: Boolean,
        actionBoot: ActionBootBrowser
) : AbstractAction(actionBoot) {

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
        actionBoot.validation()?.check(path)
    }

    override fun toStringLog() = "Action: `$actionBoot` | path: `$path`, wait: `$wait`"

}