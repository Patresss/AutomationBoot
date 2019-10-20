package com.patres.automation.mapper

import com.patres.automation.mapper.model.RootSchemaGroupSerialized
import com.patres.automation.model.RootSchemaGroupModel
import java.io.File

object RootSchemaGroupMapper {

    fun modelToSerialize(model: RootSchemaGroupModel): RootSchemaGroupSerialized {
        return RootSchemaGroupSerialized(
                schemaGroupSerialized = SchemaGroupMapper.controllerToSerialized(model.controller.schemaGroupController),
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
            controller.schemaGroupController = SchemaGroupMapper.serializedToController(serializedModel.schemaGroupSerialized)
            if (File(serializedModel.tmpFile).exists()) {
                tmpFile = File(serializedModel.tmpFile)
            }
            if (serializedModel.file != null && File(serializedModel.file).exists()) {
                file = File(serializedModel.file)
            }
        }
    }

}