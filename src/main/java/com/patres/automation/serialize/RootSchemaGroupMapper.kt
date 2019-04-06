package com.patres.automation.serialize

import com.patres.automation.model.RootSchemaGroupModel
import com.patres.automation.serialize.model.RootSchemaGroupSerialized
import java.io.File

object RootSchemaGroupMapper {

    fun modelToSerialize(model: RootSchemaGroupModel): RootSchemaGroupSerialized {
        return RootSchemaGroupSerialized(
                schemaGroupSerialized = SchemaGroupMapper.modelToSerialize(model.schemaGroup),
                localSettings = model.localSettings,
                tmpFile = model.tmpFile.absolutePath,
                file = model.file?.absolutePath,
                saved = model.saved
        )
    }

    fun serializedToModel(serializedModel: RootSchemaGroupSerialized): RootSchemaGroupModel {
        return RootSchemaGroupModel(
                localSettings = serializedModel.localSettings,
                saved = serializedModel.saved
        ).apply {
            schemaGroup = SchemaGroupMapper.serializedToModel(serializedModel.schemaGroupSerialized, this, null)
            if (File(serializedModel.tmpFile).exists()) {
                tmpFile = File(serializedModel.tmpFile)
            }
            if (serializedModel.file != null && File(serializedModel.file).exists()) {
                file = File(serializedModel.file)
            }
        }
    }

}