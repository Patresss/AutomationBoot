package com.patres.automation.mapper

import com.patres.automation.action.AbstractAction
import com.patres.automation.action.script.OpenDirectoryAction
import com.patres.automation.action.script.OpenFileAction
import com.patres.automation.action.script.WindowsRunAndWaitScriptAction
import com.patres.automation.action.script.WindowsRunScriptAction
import com.patres.automation.action.text.PasteTextFromFileAction
import com.patres.automation.action.text.TypeTextFromFileAction
import com.patres.automation.gui.controller.model.BrowseFileActionController
import com.patres.automation.mapper.model.BrowserActionSerialized
import com.patres.automation.type.ActionBootBrowser.*

object BrowserActionMapper : Mapper<BrowseFileActionController, AbstractAction, BrowserActionSerialized> {

    override fun controllerToModel(controller: BrowseFileActionController): AbstractAction {
        return when (controller.action) {
            PASTE_TEXT_FROM_FILE -> PasteTextFromFileAction(controller.value)
            TYPE_TEXT_FROM_FILE -> TypeTextFromFileAction(controller.value, controller.root?.automationRunningProperty)
            OPEN_FILE -> OpenFileAction(controller.value)
            OPEN_DIRECTORY -> OpenDirectoryAction(controller.value)
            WINDOWS_SCRIPT_RUN -> WindowsRunScriptAction(controller.value)
            WINDOWS_SCRIPT_RUN_AND_WAITE -> WindowsRunAndWaitScriptAction(controller.value)
        }
    }

    override fun controllerToSerialized(controller: BrowseFileActionController): BrowserActionSerialized {
        return controller.run {
            BrowserActionSerialized(action, value)
        }
    }

    override fun serializedToController(serialized: BrowserActionSerialized): BrowseFileActionController {
        return BrowseFileActionController(serialized.actionType).apply {
            value = serialized.path
        }
    }

}