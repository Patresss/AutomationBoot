package com.patres.automation.file

import com.patres.automation.ApplicationLauncher
import com.patres.automation.Main
import com.patres.automation.action.RootSchemaGroupModel
import com.patres.automation.gui.controller.MainController
import com.patres.automation.mapper.AutomationMapper
import com.patres.automation.mapper.RootSchemaGroupMapper
import com.patres.automation.settings.LanguageManager
import com.patres.automation.util.fromBundle
import org.slf4j.LoggerFactory
import java.io.File

object TmpFileLoader {

    private val logger = LoggerFactory.getLogger(TmpFileLoader::class.java)
    private val extension = FileType.TEMP_AUTOMATION_BOOT.extension
    private val filenameBinding = LanguageManager.createStringBinding("rootSchema.defaultFileName")

    fun createNewTmpFile(): File {
        return findValidFile().apply {
            logger.info("Creating a file $this")
            createNewFile()
        }
    }

    fun saveTmpFile(rootSchemaGroupModel: RootSchemaGroupModel) {
        if (!rootSchemaGroupModel.tmpFile.exists()) {
            ApplicationLauncher.tmpDirector.mkdir()
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
        var newFile = File(ApplicationLauncher.tmpDirector, newFileName)

        while (newFile.exists()) {
            newFileName = "$filename ${currentNumber++}.$extension"
            newFile = File(ApplicationLauncher.tmpDirector, newFileName)
        }

        return newFile
    }

}