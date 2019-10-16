package com.patres.automation.file

import com.patres.automation.Main
import com.patres.automation.file.FileConstants.DEFAULT_TMP_FILENAME
import com.patres.automation.file.FileConstants.TMP_EXTENSION
import com.patres.automation.model.RootSchemaGroupModel
import com.patres.automation.serialize.AutomationMapper
import com.patres.automation.serialize.RootSchemaGroupMapper
import java.io.File

object TmpFileLoader {

    fun createNewTmpFile(): File {
        return findValidFile().apply {
            createNewFile()
        }
    }

    fun saveTmpFile(rootSchemaGroupModel: RootSchemaGroupModel) {
//        if (!rootSchemaGroupModel.tmpFile.exists()) {
//            Main.tmpDirector.mkdir()
//            rootSchemaGroupModel.tmpFile = findValidFile()
//        }
//        val rootSchemaGroupSerialized = RootSchemaGroupMapper.modelToSerialize(rootSchemaGroupModel)
//
//        val serializedRootGroup = AutomationMapper.toJson(rootSchemaGroupSerialized)
//        rootSchemaGroupModel.tmpFile.writeText(serializedRootGroup)
    }

    private fun findValidFile(): File {
        var currentNumber = 1
        var newFileName = "$DEFAULT_TMP_FILENAME ${currentNumber++}.$TMP_EXTENSION"
        var newFile = File(Main.tmpDirector, newFileName)

        while (newFile.exists()) {
            newFileName = "$DEFAULT_TMP_FILENAME ${currentNumber++}.$TMP_EXTENSION"
            newFile = File(Main.tmpDirector, newFileName)
        }

        return newFile
    }

}