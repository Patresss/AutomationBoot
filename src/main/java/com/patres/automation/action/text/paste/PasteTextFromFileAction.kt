package com.patres.automation.action.text.paste

import com.patres.automation.gui.controller.model.BrowseFileActionController
import com.patres.automation.menuItem.MenuItem
import com.patres.automation.model.RootSchemaGroupModel
import com.patres.automation.model.SchemaGroupModel
import com.patres.automation.validation.AbstractValidation
import com.patres.automation.validation.FileExistValidation
import java.io.File


class PasteTextFromFileAction(
        root: RootSchemaGroupModel,
        parent: SchemaGroupModel = root.getSelectedSchemaGroupModel()
) : PasteTextAction<BrowseFileActionController>(root, parent) {

    companion object {
        val createAction = { root: RootSchemaGroupModel, parent: SchemaGroupModel -> PasteTextFromFileAction(root, parent) }
        val addAction = { root: RootSchemaGroupModel -> root.addNodeAction(PasteTextFromFileAction(root)) }
    }

    override val controller: BrowseFileActionController = BrowseFileActionController(this)

    override var validator: AbstractValidation? = FileExistValidation(controller).also { it.activateControlListener() }

    override fun getText() = File(controller.value).readText()

    init {
        controller.actionLabel.text = MenuItem.PASTE_TEXT.actionName
    }

}