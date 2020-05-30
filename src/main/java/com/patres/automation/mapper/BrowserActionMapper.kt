package com.patres.automation.mapper

import com.patres.automation.action.AbstractAction
import com.patres.automation.action.script.*
import com.patres.automation.action.text.PasteTextFromFileAction
import com.patres.automation.action.text.TypeTextFromFileAction
import com.patres.automation.excpetion.CannotFindRootSchemaException
import com.patres.automation.gui.controller.model.BrowseFileActionController
import com.patres.automation.mapper.model.BrowserActionSerialized
import com.patres.automation.type.ActionBootBrowser
import com.patres.automation.type.ActionBootBrowser.*
import javafx.beans.property.BooleanProperty

object BrowserActionMapper : Mapper<BrowseFileActionController, AbstractAction, BrowserActionSerialized> {

    override fun controllerToModel(controller: BrowseFileActionController): AbstractAction {
        val root = controller.root ?: throw CannotFindRootSchemaException()
        return calculateBrowserAction(controller.actionBoot, controller.value, root.automationRunningProperty)
    }

    override fun controllerToSerialized(controller: BrowseFileActionController): BrowserActionSerialized {
        return controller.run {
            BrowserActionSerialized(actionBoot, value)
        }
    }

    override fun serializedToController(serialized: BrowserActionSerialized): BrowseFileActionController {
        return BrowseFileActionController(serialized.actionBootType).apply {
            value = serialized.path
        }
    }

    override fun serializedToModel(serialized: BrowserActionSerialized, automationRunningProperty: BooleanProperty): AbstractAction {
        return calculateBrowserAction(serialized.actionBootType, serialized.path, automationRunningProperty)
    }

    private fun calculateBrowserAction(actionType: ActionBootBrowser, path: String, automationRunningProperty: BooleanProperty): AbstractAction {
        return when (actionType) {
            PASTE_TEXT_FROM_FILE -> PasteTextFromFileAction(path)
            TYPE_TEXT_FROM_FILE -> TypeTextFromFileAction(path, automationRunningProperty)
            OPEN_FILE -> OpenFileAction(path)
            OPEN_DIRECTORY -> OpenDirectoryAction(path)
            WINDOWS_SCRIPT_RUN -> WindowsRunScriptAction(path)
            WINDOWS_SCRIPT_RUN_AND_WAITE -> WindowsRunAndWaitScriptAction(path)
            RUN_EXISTING_SCHEMA -> {
                val schemaModel = RootSchemaGroupMapper.serializedToMainSchemaModel(path, automationRunningProperty)
                RunExistingSchemaAction(schemaModel, actionType)
            }
        }
    }
}