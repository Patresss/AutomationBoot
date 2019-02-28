package com.patres.automation.serialize

import com.patres.automation.model.RootSchemaGroupModel
import com.patres.automation.model.SchemaGroupModel
import com.patres.automation.serialize.model.RootSchemaGroupSerialized
import com.patres.automation.serialize.model.SchemaGroupSerialized

object RootSchemaGroupMapper {

    fun modelToSerialize(model: RootSchemaGroupModel): RootSchemaGroupSerialized {
        return RootSchemaGroupSerialized(
                schemaGroupSerialized = SchemaGroupMapper.modelToSerialize(model.schemaGroup)
        )

    }

    fun serializedToModel(serializedModel: RootSchemaGroupSerialized): RootSchemaGroupModel {
        return RootSchemaGroupModel().apply {
            schemaGroup = SchemaGroupMapper.serializedToModel(serializedModel.schemaGroupSerialized, this, null)
        }

    }

}