package com.patres.automation.serialize

import com.patres.automation.action.delay.DelayAction
import com.patres.automation.gui.controller.model.AutomationController
import com.patres.automation.model.ActionNodeModel
import com.patres.automation.model.RootSchemaGroupModel
import com.patres.automation.model.SchemaGroupModel
import com.patres.automation.serialize.model.ActionNodeSerialized
import com.patres.automation.serialize.model.DelayActionSerialized

object ActionNodeMapper {

    fun modelToSerialize(actionNode: ActionNodeModel<out AutomationController>): ActionNodeSerialized {
        return when (actionNode) {
            is DelayAction -> DelayActionSerialized(actionNode.getActionValue())
            else -> TODO("Impelemnt more mapper")
        }
    }

    fun serializedToModel(serialized: ActionNodeSerialized, root: RootSchemaGroupModel, parent: SchemaGroupModel): ActionNodeModel<out AutomationController> {
        return when (serialized) {
            is DelayActionSerialized -> DelayAction(root, parent).apply { setActionValue(serialized.actionValue) }
            else -> TODO("Impelemnt more mapper")
        }
    }


}