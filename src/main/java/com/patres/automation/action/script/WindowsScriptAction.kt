package com.patres.automation.action.script

import com.patres.automation.action.AbstractAction
import com.patres.automation.type.ActionBootBrowser
import java.io.File

class WindowsRunAndWaitScriptAction(path: String) : WindowsScriptAction(path, true, ActionBootBrowser.WINDOWS_SCRIPT_RUN_AND_WAITE)
class WindowsRunScriptAction(path: String) : WindowsScriptAction(path, false, ActionBootBrowser.WINDOWS_SCRIPT_RUN_AND_WAITE)

abstract class WindowsScriptAction(
        val path: String,
        val wait: Boolean,
        actionBoot: ActionBootBrowser
) : AbstractAction(actionBoot) {

    override fun runAction() {
        val file = File(path)

        val processBuilder = ProcessBuilder().apply {
            command(file.absolutePath)
//            directory(file.parentFile)
        }

        if (wait) {
            val process = processBuilder.start()
            process.waitFor()
        }
    }

    override fun validate() {
        actionBoot.validation()?.check(path)
    }

}