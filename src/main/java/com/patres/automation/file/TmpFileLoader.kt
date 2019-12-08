package com.patres.automation.file

import com.patres.automation.Main
import com.patres.automation.action.RootSchemaGroupModel
import com.patres.automation.mapper.AutomationMapper
import com.patres.automation.mapper.RootSchemaGroupMapper
import com.patres.automation.settings.LanguageManager
import com.patres.automation.util.fromBundle
import java.io.File

object TmpFileLoader {

    private val extension = FileType.TEMP_AUTOMATION_BOOT.extension
    private val filenameBinding = LanguageManager.createStringBinding("rootSchema.defaultFileName")

    fun createNewTmpFile(): File {
        return findValidFile().apply {
            createNewFile()
        }
    }

    fun saveTmpFile(rootSchemaGroupModel: RootSchemaGroupModel) {
        if (!rootSchemaGroupModel.tmpFile.exists()) {
            Main.tmpDirector.mkdir()
            rootSchemaGroupModel.tmpFile = findValidFile()
        }
        val rootSchemaGroupSerialized = RootSchemaGroupMapper.modelToSerialize(rootSchemaGroupModel)

        val serializedRootGroup = AutomationMapper.toJson(rootSchemaGroupSerialized)
        rootSchemaGroupModel.tmpFile.writeText(serializedRootGroup)
    }

    private fun findValidFile(): File {
        var currentNumber = 1
        val filename = filenameBinding.get()
        var newFileName = "$filename ${currentNumber++}.$extension"
        var newFile = File(Main.tmpDirector, newFileName)

        while (newFile.exists()) {
            newFileName = "$filename ${currentNumber++}.$extension"
            newFile = File(Main.tmpDirector, newFileName)
        }

        return newFile
    }

}