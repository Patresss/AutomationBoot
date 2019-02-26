package com.patres.automation.serialize

import com.patres.automation.action.delay.DelayAction
import com.patres.automation.gui.controller.model.AutomationController
import com.patres.automation.model.ActionNodeModel
import com.patres.automation.model.AutomationModel
import com.patres.automation.model.RootSchemaGroupModel
import com.patres.automation.model.SchemaGroupModel
import com.patres.automation.serialize.model.ActionNodeSerialized
import com.patres.automation.serialize.model.AutomationActionSerialized
import com.patres.automation.serialize.model.DelayActionSerialized
import com.patres.automation.serialize.model.SchemaGroupSerialized

object AutomationActionMapper {

    fun modelToSerialize(automationModel: AutomationModel<out AutomationController>): AutomationActionSerialized {
        return when (automationModel) {
            is SchemaGroupModel -> SchemaGroupMapper.modelToSerialize(automationModel)
            is ActionNodeModel -> ActionNodeMapper.modelToSerialize(automationModel)
            else -> throw Exception("Cannot find actionModel for map")
        }
    }

    fun serializedToModel(serialized: AutomationActionSerialized, root: RootSchemaGroupModel, parent: SchemaGroupModel): AutomationModel<out AutomationController> {
        return when (serialized) {
            is SchemaGroupSerialized -> SchemaGroupMapper.serializedToModel(serialized, root, parent)
            is ActionNodeSerialized -> ActionNodeMapper.serializedToModel(serialized, root, parent)
            else -> throw Exception("Cannot find actionModel for map")
        }
    }

}