package com.patres.automation.action

import com.patres.automation.file.FileType
import java.io.File

class RootSchemaFiles(
        var currentFile: File,
        var orgFile: File? = null
) {

    fun getName() = orgFile?.nameWithoutExtension ?: currentFile.nameWithoutExtension

    fun getFileToSave() = orgFile ?: currentFile

    fun calculateTmpFile(): File? {
        return if (currentFileIsTemp()) {
            currentFile
        } else {
            null
        }
    }

    fun isNewTmpFile() = currentFileIsTemp() && orgFile == null

    fun saveNewFile(file: File) {
        if (calculateTmpFile() != file) {
            calculateTmpFile()?.delete()
        }
        currentFile = file
        orgFile = null
    }

    fun removeTmpFile() {
        calculateTmpFile()?.delete()
    }

    fun setNewTmpFile(tmpFile: File) {
        orgFile = currentFile
        currentFile = tmpFile
    }

    fun isRelated(fileToOpen: File) = currentFile == fileToOpen || orgFile == fileToOpen

    fun currentFileIsTemp() = currentFile.extension == FileType.TEMP_AUTOMATION_BOOT.extension

    fun getFilePathToSettings(): String = currentFile.absolutePath

}
