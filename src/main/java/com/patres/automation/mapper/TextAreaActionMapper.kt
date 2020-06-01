package com.patres.automation.mapper

import com.patres.automation.action.AbstractAction
import com.patres.automation.action.text.PasteTextFromFieldAction
import com.patres.automation.action.text.TypeTextFromFieldAction
import com.patres.automation.excpetion.CannotFindRootSchemaException
import com.patres.automation.gui.controller.model.TextAreaActionController
import com.patres.automation.mapper.model.TextAreaActionSerialized
import com.patres.automation.type.ActionBootTextArea
import javafx.beans.property.BooleanProperty

object TextAreaActionMapper : Mapper<TextAreaActionController, AbstractAction, TextAreaActionSerialized> {

    override fun controllerToModel(controller: TextAreaActionController, automationRunningProperty: BooleanProperty): AbstractAction {
        return calculateTextAreaModel(controller.actionBoot, controller.value, automationRunningProperty)
    }

    override fun controllerToSerialized(controller: TextAreaActionController): TextAreaActionSerialized {
        return controller.run {
            TextAreaActionSerialized(actionBoot, value)
        }
    }

    override fun serializedToController(serialized: TextAreaActionSerialized): TextAreaActionController {
        return TextAreaActionController(serialized.actionBootType).apply {
            value = serialized.value
        }
    }

    override fun serializedToModel(serialized: TextAreaActionSerialized, automationRunningProperty: BooleanProperty): AbstractAction {
        return calculateTextAreaModel(serialized.actionBootType, serialized.value, automationRunningProperty)
    }

    private fun calculateTextAreaModel(actionType: ActionBootTextArea, value: String, automationRunningProperty: BooleanProperty): AbstractAction {
        return when (actionType) {
            ActionBootTextArea.PASTE_TEXT -> PasteTextFromFieldAction(value)
            ActionBootTextArea.TYPE_TEXT -> TypeTextFromFieldAction(value, automationRunningProperty)
        }
    }


}