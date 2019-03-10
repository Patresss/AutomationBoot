package com.patres.automation.action.text.paste

import com.patres.automation.gui.controller.model.TextAreaActionController
import com.patres.automation.menuItem.MenuItem
import com.patres.automation.model.RootSchemaGroupModel
import com.patres.automation.model.SchemaGroupModel


class PasteTextFromFieldAction(
        root: RootSchemaGroupModel,
        parent: SchemaGroupModel = root.getSelectedSchemaGroupModel()
) : PasteTextAction<TextAreaActionController>(root, parent) {

    companion object {
        val createAction= { root: RootSchemaGroupModel, parent: SchemaGroupModel -> PasteTextFromFieldAction(root, parent) }
        val addAction = { root: RootSchemaGroupModel -> root.addNodeAction(PasteTextFromFieldAction(root)) }
    }

    override val controller: TextAreaActionController = TextAreaActionController(this)

    override fun getText() = controller.value

    init {
        controller.actionLabel.text = MenuItem.PASTE_TEXT.actionName
    }

}