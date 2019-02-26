package com.patres.automation.serialize

import com.patres.automation.model.RootSchemaGroupModel
import com.patres.automation.model.SchemaGroupModel
import com.patres.automation.serialize.model.SchemaGroupSerialized

object SchemaGroupMapper {

    fun modelToSerialize(schemaGroupModel: SchemaGroupModel): SchemaGroupSerialized {
        return SchemaGroupSerialized(
                actionList = schemaGroupModel.actionBlocks.map { AutomationActionMapper.modelToSerialize(it) },
                groupName = schemaGroupModel.getGroupName(),
                numberOfIterations = schemaGroupModel.getNumberOfIteration().toString()
        )
    }

    fun serializedToModel(serialized: SchemaGroupSerialized, root: RootSchemaGroupModel, parent: SchemaGroupModel?): SchemaGroupModel {
        return SchemaGroupModel(root, parent).apply {
            setGroupName(serialized.groupName)
            setNumberOfIterations(serialized.numberOfIterations)
            val models = serialized.actionList.map { AutomationActionMapper.serializedToModel(it, root, this) }
            models.forEach {
                addActionBlocks(it)
            }
        }
    }

}