package com.patres.automation.mapper

import com.patres.automation.action.AbstractAction
import com.patres.automation.action.script.*
import com.patres.automation.action.text.PasteTextFromFileAction
import com.patres.automation.action.text.TypeTextFromFileAction
import com.patres.automation.excpetion.CannotFindRootSchemaException
import com.patres.automation.gui.controller.box.AbstractBox
import com.patres.automation.gui.controller.model.BrowseFileActionController
import com.patres.automation.mapper.model.BrowserActionSerialized
import com.patres.automation.type.ActionBootBrowser
import com.patres.automation.type.ActionBootBrowser.*
import javafx.beans.property.BooleanProperty

object BrowserActionMapper : Mapper<BrowseFileActionController, AbstractAction, BrowserActionSerialized> {

    override fun controllerToModel(controller: BrowseFileActionController, automationRunningProperty: BooleanProperty): AbstractAction {
        return calculateBrowserAction(controller.actionBoot, controller.value, automationRunningProperty, controller.box)
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

    private fun calculateBrowserAction(actionType: ActionBootBrowser, path: String, automationRunningProperty: BooleanProperty, box: AbstractBox<*>? = null): AbstractAction {
        return when (actionType) {
            PASTE_TEXT_FROM_FILE -> PasteTextFromFileAction(path, box)
            TYPE_TEXT_FROM_FILE -> TypeTextFromFileAction(path, automationRunningProperty, box)
            OPEN_FILE -> OpenFileAction(path, box)
            OPEN_DIRECTORY -> OpenDirectoryAction(path, box)
            WINDOWS_SCRIPT_RUN -> WindowsRunScriptAction(path, box)
            WINDOWS_SCRIPT_RUN_AND_WAITE -> WindowsRunAndWaitScriptAction(path, box)
            RUN_EXISTING_SCHEMA -> {
                val schemaModel = RootSchemaGroupMapper.serializedToMainSchemaModel(path, automationRunningProperty)
                RunExistingSchemaAction(schemaModel, actionType, box)
            }
        }
    }

}