package com.patres.automation.serialize

import com.patres.automation.model.RootSchemaGroupModel
import com.patres.automation.model.SchemaGroupModel
import com.patres.automation.serialize.model.SchemaGroupSerialized

object SchemaGroupMapper {

    fun modelToSerialize(model: SchemaGroupModel): SchemaGroupSerialized {
        return SchemaGroupSerialized(
                actionList = model.actionBlocks.map { AutomationActionMapper.modelToSerialize(it) },
                groupName = model.getGroupName(),
                numberOfIterations = model.getNumberOfIteration().toString()
        )
    }

    fun serializedToModel(serializedModel: SchemaGroupSerialized, root: RootSchemaGroupModel, parent: SchemaGroupModel?): SchemaGroupModel {
        return SchemaGroupModel(root, parent).apply {
            setGroupName(serializedModel.groupName)
            setNumberOfIterations(serializedModel.numberOfIterations)
            val models = serializedModel.actionList.map { AutomationActionMapper.serializedToModel(it, root, this) }
            models.forEach {
                addActionBlocks(it)
            }
        }
    }

}