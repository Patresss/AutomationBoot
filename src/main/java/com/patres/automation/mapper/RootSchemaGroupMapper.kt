package com.patres.automation.mapper

import com.patres.automation.action.ActionRunner
import com.patres.automation.action.RootSchemaFiles
import com.patres.automation.action.RootSchemaGroupModel
import com.patres.automation.action.SchemaGroupModel
import com.patres.automation.file.FileType
import com.patres.automation.gui.controller.model.RootSchemaGroupController
import com.patres.automation.mapper.model.RootSchemaGroupSerialized
import com.patres.automation.system.ApplicationInfo
import com.patres.automation.validation.FileExistValidation
import com.patres.automation.validation.FileExtensionValidation
import javafx.beans.property.BooleanProperty
import javafx.beans.property.SimpleBooleanProperty
import org.slf4j.LoggerFactory
import java.io.File

object RootSchemaGroupMapper {

    private val logger = LoggerFactory.getLogger(RootSchemaGroupMapper::class.java)

    fun serializedToModel(serializedModel: RootSchemaGroupSerialized, rootSchemaFile: File): RootSchemaGroupModel {
        logger.debug("RootSchemaGroup Mapping: Serialized to Model")
        val orgFile = serializedModel.orgFile?.let { File(it) }
        val rootSchemaFiles = RootSchemaFiles(rootSchemaFile, orgFile)
        val automationRunningProperty = SimpleBooleanProperty(false)
        return RootSchemaGroupModel(
                schemaGroupModel = SchemaGroupMapper.serializedToModel(serializedModel.schemaGroupSerialized, automationRunningProperty),
                actionRunner = ActionRunner(automationRunningProperty, serializedModel.localSettings, rootSchemaFiles)
        )
    }

    fun serializedToMainSchemaModel(filePath: String, automationRunningProperty: BooleanProperty): SchemaGroupModel {
        validateAutomationBootFile(filePath)
        val file = File(filePath)
        val serializedRootGroup = file.readText()
        val rootGroupSerialized: RootSchemaGroupSerialized = AutomationMapper.toObject(serializedRootGroup)

        return SchemaGroupMapper.serializedToModel(rootGroupSerialized.schemaGroupSerialized, automationRunningProperty)
    }


    fun controllerToModel(rootSchemaController: RootSchemaGroupController): RootSchemaGroupModel {
        logger.debug("RootSchemaGroup Mapping: Controller to Model")
        return RootSchemaGroupModel(
                schemaGroupModel = SchemaGroupMapper.controllerToModel(rootSchemaController.schemaGroupController, rootSchemaController.actionRunner.automationRunningProperty),
                actionRunner = rootSchemaController.actionRunner
        )
    }

    fun controllerToSerialize(rootSchemaController: RootSchemaGroupController): RootSchemaGroupSerialized {
        logger.debug("RootSchemaGroup Mapping: Controller to Serialize")
        return RootSchemaGroupSerialized(
                schemaGroupSerialized = SchemaGroupMapper.controllerToSerialized(rootSchemaController.schemaGroupController),
                localSettings = rootSchemaController.actionRunner.localSettings,
                orgFile = rootSchemaController.actionRunner.rootFiles.orgFile?.absolutePath,
                applicationVersion = ApplicationInfo.version
        )
    }

    fun serializedToController(rootGroupSerialized: RootSchemaGroupSerialized, fileToOpen: File): RootSchemaGroupController {
        logger.debug("RootSchemaGroup Mapping: Serialized to Controller")
        val orgFile = rootGroupSerialized.orgFile?.let { File(it) }
        val rootSchemaFiles = RootSchemaFiles(fileToOpen, orgFile)
        return RootSchemaGroupController(
                actionRunner = ActionRunner(SimpleBooleanProperty(false), rootGroupSerialized.localSettings, rootSchemaFiles),
                schemaGroupController = SchemaGroupMapper.serializedToController(rootGroupSerialized.schemaGroupSerialized)
        )
    }

    private fun validateAutomationBootFile(filePath: String) {
        FileExistValidation().check(filePath)
        FileExtensionValidation(FileType.AUTOMATION_BOOT).check(filePath)
    }

}