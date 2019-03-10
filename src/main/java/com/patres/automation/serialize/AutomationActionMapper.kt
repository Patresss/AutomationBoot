package com.patres.automation.serialize

import com.patres.automation.action.TextActionModel
import com.patres.automation.action.keyboard.KeyboardButtonAction
import com.patres.automation.gui.controller.model.AutomationController
import com.patres.automation.model.AutomationModel
import com.patres.automation.model.RootSchemaGroupModel
import com.patres.automation.model.SchemaGroupModel
import com.patres.automation.serialize.model.AutomationActionSerialized
import com.patres.automation.serialize.model.KeyboardFieldActionSerialized
import com.patres.automation.serialize.model.SchemaGroupSerialized
import com.patres.automation.serialize.model.TextActionSerialized

object AutomationActionMapper : Mapper<AutomationModel<out AutomationController>, AutomationActionSerialized> {

    override fun modelToSerialize(model: AutomationModel<out AutomationController>): AutomationActionSerialized {
        return when (model) {
            is SchemaGroupModel -> SchemaGroupMapper.modelToSerialize(model)
            is TextActionModel -> TextFieldActionMapper.modelToSerialize(model)
            is KeyboardButtonAction -> KeyboardFieldActionMapper.modelToSerialize(model)
            else -> throw Exception("Cannot find actionModel for map")
        }
    }

    override fun serializedToModel(serializedModel: AutomationActionSerialized, root: RootSchemaGroupModel, parent: SchemaGroupModel): AutomationModel<out AutomationController> {
        return when (serializedModel) {
            is SchemaGroupSerialized -> SchemaGroupMapper.serializedToModel(serializedModel, root, parent)
            is TextActionSerialized -> TextFieldActionMapper.serializedToModel(serializedModel, root, parent)
            is KeyboardFieldActionSerialized -> KeyboardFieldActionMapper.serializedToModel(serializedModel, root, parent)
            else -> throw Exception("Cannot find actionModel for map")
        }
    }

}