package com.patres.automation.action.text.type

import com.patres.automation.gui.controller.model.BrowseFileActionController
import com.patres.automation.menuItem.MenuItem
import com.patres.automation.model.RootSchemaGroupModel
import com.patres.automation.model.SchemaGroupModel
import com.patres.automation.validation.AbstractValidation
import com.patres.automation.validation.FileValidation
import java.io.File


class TypeTextFromFileAction(
        root: RootSchemaGroupModel,
        parent: SchemaGroupModel = root.getSelectedSchemaGroupModel()
) : TypeTextAction<BrowseFileActionController>(root, parent) {

    companion object {
        val createAction = { root: RootSchemaGroupModel, parent: SchemaGroupModel -> TypeTextFromFileAction(root, parent) }
        val addAction = { root: RootSchemaGroupModel -> root.addNodeAction(TypeTextFromFileAction(root)) }
    }

    override val controller: BrowseFileActionController = BrowseFileActionController(this)

    override var validator: AbstractValidation? = FileValidation(controller).also { it.activateControlListener() }

    override fun getText() = File(controller.value).readText()

    init {
        controller.actionLabel.text = MenuItem.TYPE_TEXT.actionName
    }

}