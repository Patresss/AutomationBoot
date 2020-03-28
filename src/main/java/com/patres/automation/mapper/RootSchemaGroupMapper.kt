package com.patres.automation.mapper

import com.patres.automation.action.RootSchemaFiles
import com.patres.automation.action.RootSchemaGroupModel
import com.patres.automation.action.SchemaGroupModel
import com.patres.automation.file.FileType
import com.patres.automation.mapper.model.RootSchemaGroupSerialized
import com.patres.automation.validation.FileExistValidation
import com.patres.automation.validation.FileExtensionValidation
import javafx.beans.property.BooleanProperty
import java.io.File

object RootSchemaGroupMapper {


    fun modelToSerialize(model: RootSchemaGroupModel, orgFile: File?): RootSchemaGroupSerialized {
        return RootSchemaGroupSerialized(
                schemaGroupSerialized = SchemaGroupMapper.controllerToSerialized(model.controller.schemaGroupController),
                localSettings = model.localSettings,
                orgFile = orgFile?.absolutePath
        )
    }

    fun modelToSerialize(model: RootSchemaGroupModel): RootSchemaGroupSerialized {
        return modelToSerialize(model, model.rootFiles.orgFile)
    }

    fun serializedToModel(serializedModel: RootSchemaGroupSerialized, rootSchemaFile: File): RootSchemaGroupModel {
        val orgFile = serializedModel.orgFile?.let { File(it) }
        val rootSchemaFiles = RootSchemaFiles(rootSchemaFile, orgFile)
        return RootSchemaGroupModel(
                localSettings = serializedModel.localSettings,
                rootFiles = rootSchemaFiles
        ).apply {
            controller.schemaGroupController = SchemaGroupMapper.serializedToController(serializedModel.schemaGroupSerialized)
        }
    }

    fun serializedToMainSchemaModel(filePath: String, automationRunningProperty: BooleanProperty?): SchemaGroupModel {
        validateAutomationBootFile(filePath)
        val file = File(filePath)
        val serializedRootGroup = file.readText()
        val rootGroupSerialized: RootSchemaGroupSerialized = AutomationMapper.toObject(serializedRootGroup)

        return SchemaGroupMapper.serializedToModel(rootGroupSerialized.schemaGroupSerialized, automationRunningProperty)
    }

    private fun validateAutomationBootFile(filePath: String) {
        FileExistValidation().check(filePath)
        FileExtensionValidation(FileType.AUTOMATION_BOOT).check(filePath)
    }

}