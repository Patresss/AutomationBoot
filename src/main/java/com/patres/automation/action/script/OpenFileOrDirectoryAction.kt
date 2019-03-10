package com.patres.automation.action.script

import com.patres.automation.action.BrowseFileAction
import com.patres.automation.action.TextActionModel
import com.patres.automation.gui.controller.model.BrowseFileActionController
import com.patres.automation.menuItem.MenuItem
import com.patres.automation.model.RootSchemaGroupModel
import com.patres.automation.model.SchemaGroupModel
import com.patres.automation.validation.AbstractValidation
import com.patres.automation.validation.FileExistValidation
import com.patres.automation.validation.FileExtensionValidation
import com.patres.automation.validation.FileOrDirectoryExistValidation
import java.awt.Desktop
import java.io.File


class OpenFileOrDirectoryAction(
        root: RootSchemaGroupModel,
        parent: SchemaGroupModel = root.getSelectedSchemaGroupModel()
) : BrowseFileAction(root, parent) {

    companion object {
        val createAction = { root: RootSchemaGroupModel, parent: SchemaGroupModel -> OpenFileOrDirectoryAction(root, parent) }
        val addAction = { root: RootSchemaGroupModel -> root.addNodeAction(OpenFileOrDirectoryAction(root)) }
    }

    override var validator: AbstractValidation? = FileOrDirectoryExistValidation(controller).also { it.activateControlListener() }

    init {
        controller.actionLabel.text = MenuItem.OPEN_FILE_OR_DIRECTORY.actionName
    }

    override fun runAction() {
        val file = File(getActionValue())
        val desktop =  Desktop.getDesktop()
        desktop.open(file)
    }

}