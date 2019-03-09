package com.patres.automation.serialize

import com.patres.automation.action.keyboard.KeyboardButtonAction
import com.patres.automation.action.keyboard.PressKeyboardButtonAction
import com.patres.automation.excpetion.ApplicationException
import com.patres.automation.menuItem.MenuItem
import com.patres.automation.model.RootSchemaGroupModel
import com.patres.automation.model.SchemaGroupModel
import com.patres.automation.serialize.model.KeyboardFieldActionSerialized


object KeyboardFieldActionMapper : Mapper<KeyboardButtonAction, KeyboardFieldActionSerialized> {

    private val actionInstanceMap = mapOf(
            MenuItem.PRESS_KEYBOARD_BUTTON.name to { root: RootSchemaGroupModel, parent: SchemaGroupModel -> PressKeyboardButtonAction(root, parent) }
    )

    private val actionClassMap = mapOf<Class<out KeyboardButtonAction>, String>(
            PressKeyboardButtonAction::class.java to MenuItem.PRESS_KEYBOARD_BUTTON.name
    )

    override fun modelToSerialize(model: KeyboardButtonAction): KeyboardFieldActionSerialized {
        val actionName: String = actionClassMap[model.javaClass]
                ?: throw ApplicationException("Cannot find action name ${model.javaClass} to serialize")
        return KeyboardFieldActionSerialized(model.getActionValue(), actionName)
    }

    override fun serializedToModel(serializedModel: KeyboardFieldActionSerialized, root: RootSchemaGroupModel, parent: SchemaGroupModel): KeyboardButtonAction {
        return actionInstanceMap[serializedModel.actionName]?.invoke(root, parent)?.apply { setActionValue(serializedModel.actionNodeValue) }
                ?: throw ApplicationException("Cannot find model ${serializedModel.actionName} to serialize")
    }

}


