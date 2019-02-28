package com.patres.automation.serialize

import com.patres.automation.action.mouse.TextFieldActionModel
import com.patres.automation.excpetion.ApplicationException
import com.patres.automation.menuItem.MenuItem
import com.patres.automation.model.RootSchemaGroupModel
import com.patres.automation.model.SchemaGroupModel
import com.patres.automation.serialize.model.TextFieldActionSerialized

object TextFieldActionMapper : Mapper<TextFieldActionModel, TextFieldActionSerialized> {

    override fun modelToSerialize(model: TextFieldActionModel): TextFieldActionSerialized {
        val actionName = MenuItem.values().find { it.classValue == model.javaClass }?.name
                ?: throw ApplicationException("Cannot find action name ${model.javaClass} to serialize")
        return TextFieldActionSerialized(model.getActionValue(), actionName)
    }

    override fun serializedToModel(serializedModel: TextFieldActionSerialized, root: RootSchemaGroupModel, parent: SchemaGroupModel): TextFieldActionModel {
        val textAction = MenuItem.values().find { it.name == serializedModel.actionName }
        val constructor = textAction?.classValue?.getConstructor(RootSchemaGroupModel::class.java, SchemaGroupModel::class.java)
        return constructor?.newInstance(root, parent)?.apply { setActionValue(serializedModel.actionNodeValue) }
                ?: throw ApplicationException("Cannot find model ${serializedModel.actionName} to serialize")
    }

}


