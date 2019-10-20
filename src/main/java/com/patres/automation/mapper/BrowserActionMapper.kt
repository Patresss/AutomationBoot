package com.patres.automation.mapper

import com.patres.automation.action.AbstractAction
import com.patres.automation.action.script.OpenFileOrDirectoryAction
import com.patres.automation.action.script.WindowsRunAndWaitScriptAction
import com.patres.automation.action.script.WindowsRunScriptAction
import com.patres.automation.action.text.PasteTextFromFileAction
import com.patres.automation.action.text.TypeTextFromFileAction
import com.patres.automation.gui.controller.model.BrowseFileActionController
import com.patres.automation.mapper.model.BrowserActionSerialized
import com.patres.automation.type.ActionBootBrowser

object BrowserActionMapper : Mapper<BrowseFileActionController, AbstractAction, BrowserActionSerialized> {

    override fun controllerToModel(controller: BrowseFileActionController): AbstractAction {
        return when (controller.action) {
            ActionBootBrowser.PASTE_TEXT_FROM_FILE -> PasteTextFromFileAction(controller.value)
            ActionBootBrowser.TYPE_TEXT_FROM_FILE -> TypeTextFromFileAction(controller.value, controller.root?.automationRunningProperty)
            ActionBootBrowser.OPEN_FILE_OR_DIRECTORY -> OpenFileOrDirectoryAction(controller.value)
            ActionBootBrowser.WINDOWS_SCRIPT_RUN -> WindowsRunScriptAction(controller.value)
            ActionBootBrowser.WINDOWS_SCRIPT_RUN_AND_WAITE -> WindowsRunAndWaitScriptAction(controller.value)
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