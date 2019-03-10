package com.patres.automation.action.script

import com.patres.automation.menuItem.MenuItem
import com.patres.automation.model.RootSchemaGroupModel
import com.patres.automation.model.SchemaGroupModel


class WindowsRunScriptAction(
        root: RootSchemaGroupModel,
        parent: SchemaGroupModel = root.getSelectedSchemaGroupModel()
) : WindowsScriptAction(root, parent) {

    companion object {
        val createAction = { root: RootSchemaGroupModel, parent: SchemaGroupModel -> WindowsRunScriptAction(root, parent) }
        val addAction = { root: RootSchemaGroupModel -> root.addNodeAction(WindowsRunScriptAction(root)) }
    }

    init {
        controller.actionLabel.text = MenuItem.WINDOWS_SCRIPT_RUN.actionName
    }

    override fun shouldWait(): Boolean = false
}