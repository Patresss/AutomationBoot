package com.patres.automation.serialize

import com.patres.automation.model.RootSchemaGroupModel
import com.patres.automation.serialize.model.RootSchemaGroupSerialized

object RootSchemaGroupMapper {

    fun modelToSerialize(model: RootSchemaGroupModel): RootSchemaGroupSerialized {
        return RootSchemaGroupSerialized(
                schemaGroupSerialized = SchemaGroupMapper.modelToSerialize(model.schemaGroup),
                localSettings = model.localSettings
        )

    }

    fun serializedToModel(serializedModel: RootSchemaGroupSerialized): RootSchemaGroupModel {
        return RootSchemaGroupModel(localSettings = serializedModel.localSettings).apply {
            schemaGroup = SchemaGroupMapper.serializedToModel(serializedModel.schemaGroupSerialized, this, null)
        }
    }

}