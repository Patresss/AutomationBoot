package com.patres.automation.serialize

import com.patres.automation.model.RootSchemaGroupModel
import com.patres.automation.model.SchemaGroupModel
import com.patres.automation.serialize.model.RootSchemaGroupSerialized
import com.patres.automation.serialize.model.SchemaGroupSerialized

object RootSchemaGroupMapper {

    fun modelToSerialize(rootSchemaGroupModel: RootSchemaGroupModel): RootSchemaGroupSerialized {
        return RootSchemaGroupSerialized(
                schemaGroupSerialized = SchemaGroupMapper.modelToSerialize(rootSchemaGroupModel.schemaGroup)
        )

    }

    fun serializedToModel(serialized: RootSchemaGroupSerialized): RootSchemaGroupModel {
        return RootSchemaGroupModel().apply {
            schemaGroup = SchemaGroupMapper.serializedToModel(serialized.schemaGroupSerialized, this, null)
        }

    }

}