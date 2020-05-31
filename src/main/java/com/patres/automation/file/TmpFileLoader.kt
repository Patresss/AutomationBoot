package com.patres.automation.file

import com.patres.automation.ApplicationLauncher
import com.patres.automation.action.RootSchemaGroupModel
import com.patres.automation.excpetion.ApplicationException
import com.patres.automation.gui.controller.model.RootSchemaGroupController
import com.patres.automation.mapper.AutomationMapper
import com.patres.automation.mapper.RootSchemaGroupMapper
import com.patres.automation.settings.GlobalSettingsLoader
import com.patres.automation.settings.LanguageManager
import java.io.File

object TmpFileLoader {

    private val extension = FileType.TEMP_AUTOMATION_BOOT.extension
    private val filenameBinding = LanguageManager.createStringBinding("rootSchema.defaultFileName")

    fun createNewTmpFile(): File {
        return findValidFile().apply {
            parentFile.mkdirs()
            createNewFile()
        }
    }

    fun saveTmpFile(rootSchemaController: RootSchemaGroupController) {
        if (rootSchemaController.actionRunner.rootFiles.calculateTmpFile() == null) {
            val createNewTmpFile = createNewTmpFile()
            rootSchemaController.actionRunner.rootFiles.setNewTmpFile(createNewTmpFile)
            GlobalSettingsLoader.save()
        }
        val tmpFile = rootSchemaController.actionRunner.rootFiles.calculateTmpFile()
                ?: throw ApplicationException("Temp file for ${rootSchemaController.actionRunner.getName()} cannot be found")
        val rootSchemaGroupSerialized = RootSchemaGroupMapper.controllerToSerialize(rootSchemaController)
        val serializedRootGroup = AutomationMapper.toJson(rootSchemaGroupSerialized)

        tmpFile.writeText(serializedRootGroup)
    }


    private fun findValidFile(): File {
        var currentNumber = 1
        val filename = filenameBinding.get()
        var newFileName = "$filename ${currentNumber++}.$extension"
        var newFile = File(ApplicationLauncher.tmpDirector, newFileName)
        while (newFile.exists()) {
            newFileName = "$filename ${currentNumber++}.$extension"
            newFile = File(ApplicationLauncher.tmpDirector, newFileName)
        }

        return newFile
    }

}