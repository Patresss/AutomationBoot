package com.patres.automation.action.text.type

import com.patres.automation.gui.controller.model.TextAreaActionController
import com.patres.automation.menuItem.MenuItem
import com.patres.automation.model.RootSchemaGroupModel
import com.patres.automation.model.SchemaGroupModel


class TypeTextFromFieldAction(
        root: RootSchemaGroupModel,
        parent: SchemaGroupModel = root.getSelectedSchemaGroupModel()
) : TypeTextAction<TextAreaActionController>(root, parent) {

    companion object {
        val createAction = { root: RootSchemaGroupModel, parent: SchemaGroupModel -> TypeTextFromFieldAction(root, parent) }
        val addAction = { root: RootSchemaGroupModel -> root.addNodeAction(TypeTextFromFieldAction(root)) }
    }

    override val controller: TextAreaActionController = TextAreaActionController(this)

    override fun getText() = controller.value

    init {
        controller.actionLabel.text = MenuItem.TYPE_TEXT_FROM_FILE.actionName
    }

}