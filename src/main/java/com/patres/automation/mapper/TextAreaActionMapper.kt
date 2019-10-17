package com.patres.automation.mapper

import com.patres.automation.action.AbstractAction
import com.patres.automation.action.text.PasteTextFromFieldAction
import com.patres.automation.action.text.TypeTextFromFieldAction
import com.patres.automation.gui.controller.model.SchemaGroupController
import com.patres.automation.gui.controller.model.TextAreaActionController
import com.patres.automation.mapper.model.TextAreaActionSerialized
import com.patres.automation.model.RootSchemaGroupModel
import com.patres.automation.type.ActionBootTextArea

object TextAreaActionMapper : Mapper<TextAreaActionController, AbstractAction, TextAreaActionSerialized> {

    override fun controllerToModel(controller: TextAreaActionController): AbstractAction {
        return when (controller.action) {
            ActionBootTextArea.PASTE_TEXT -> PasteTextFromFieldAction(controller.value)
            ActionBootTextArea.TYPE_TEXT -> TypeTextFromFieldAction(controller.value, controller.root.automationRunningProperty)
        }
    }

    override fun controllerToSerialized(controller: TextAreaActionController): TextAreaActionSerialized {
        return controller.run {
            TextAreaActionSerialized(action, value)
        }
    }

    override fun serializedToController(serialized: TextAreaActionSerialized, root: RootSchemaGroupModel, parent: SchemaGroupController?): TextAreaActionController {
        return TextAreaActionController(root, parent, serialized.actionType).apply {
            value = serialized.value
        }
    }

}