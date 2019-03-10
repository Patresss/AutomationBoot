package com.patres.automation.action.script

import com.patres.automation.menuItem.MenuItem
import com.patres.automation.model.RootSchemaGroupModel
import com.patres.automation.model.SchemaGroupModel


class WindowsRunAndWaitScriptAction(
        root: RootSchemaGroupModel,
        parent: SchemaGroupModel = root.getSelectedSchemaGroupModel()
) : WindowsScriptAction(root, parent) {


    companion object {
        val createAction = { root: RootSchemaGroupModel, parent: SchemaGroupModel -> WindowsRunAndWaitScriptAction(root, parent) }
        val addAction = { root: RootSchemaGroupModel -> root.addNodeAction(WindowsRunAndWaitScriptAction(root)) }
    }

    init {
        controller.actionLabel.text = MenuItem.WINDOWS_SCRIPT_RUN_AND_WAITE.actionName
    }

    override fun shouldWait(): Boolean = true
}