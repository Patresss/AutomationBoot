package com.patres.automation.mapper

import com.patres.automation.action.RootSchemaGroupModel
import com.patres.automation.mapper.model.RootSchemaGroupSerialized
import java.io.File

object RootSchemaGroupMapper {

    fun modelToSerialize(model: RootSchemaGroupModel): RootSchemaGroupSerialized {
        return modelToSerializeWithFileName(model, model.file)
    }

    fun modelToSerializeWithFileName(model: RootSchemaGroupModel, file: File?): RootSchemaGroupSerialized {
        return RootSchemaGroupSerialized(
                schemaGroupSerialized = SchemaGroupMapper.controllerToSerialized(model.controller.schemaGroupController),
                localSettings = model.localSettings,
                tmpFile = model.tmpFile.absolutePath,
                name = file?.nameWithoutExtension,
                saved = model.saved
        )
    }

    fun serializedToModel(serializedModel: RootSchemaGroupSerialized, rootSchemaFile: File): RootSchemaGroupModel {
        return RootSchemaGroupModel(
                localSettings = serializedModel.localSettings,
                saved = serializedModel.saved
        ).apply {
            controller.schemaGroupController = SchemaGroupMapper.serializedToController(serializedModel.schemaGroupSerialized)
            if (File(serializedModel.tmpFile).exists()) {
                tmpFile = File(serializedModel.tmpFile)
            }
            if (rootSchemaFile.exists()) {
                file = rootSchemaFile
            }
        }

    }

}