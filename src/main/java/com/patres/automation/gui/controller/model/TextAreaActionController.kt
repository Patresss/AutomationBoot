package com.patres.automation.gui.controller.model

import com.patres.automation.action.AbstractAction
import com.patres.automation.action.text.PasteTextFromFieldAction
import com.patres.automation.action.text.TypeTextFromFieldAction
import com.patres.automation.model.RootSchemaGroupModel
import com.patres.automation.type.ActionBootTextArea

class TextAreaActionController(
        root: RootSchemaGroupModel,
        parent: SchemaGroupController,
        action: ActionBootTextArea
) : TextActionController<ActionBootTextArea>("AreaFieldAction.fxml", root, parent, action) {

    override fun toModel(): AbstractAction {
        return when (action) {
            ActionBootTextArea.PASTE_TEXT -> PasteTextFromFieldAction(value)
            ActionBootTextArea.TYPE_TEXT -> TypeTextFromFieldAction(value, root.automationRunningProperty)
        }
    }
}