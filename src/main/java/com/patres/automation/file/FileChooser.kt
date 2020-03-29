package com.patres.automation.file

import com.patres.automation.ApplicationLauncher
import com.patres.automation.settings.GlobalSettingsLoader
import com.patres.automation.settings.LanguageManager
import javafx.stage.DirectoryChooser
import javafx.stage.FileChooser
import java.io.File

class FileChooser(
        fileType: FileType?,
        private val director: Boolean = false
) {

    private val extFilter = if (fileType != null) FileChooser.ExtensionFilter("${fileType.type} (*.${fileType.extension})", "*.${fileType.extension}") else null

    fun chooseToLoad(pathTarget: String? = null): File? {
        return if (director) chooseFileOrDirectoryToLoad(pathTarget) else chooseFileToLoad(pathTarget)
    }

    private fun chooseFileToLoad(pathTarget: String? = null): File? {
        val fileChooser = getFileChooser()
        pathTarget?.let {
            val file = File(it)
            if (file.exists()) {
                fileChooser.initialDirectory = file.parentFile
            }
        }
        fileChooser.titleProperty().bind(LanguageManager.createStringBinding("action.chooseFile"))
        return fileChooser.showOpenDialog(ApplicationLauncher.mainStage)?.apply {
            saveLastOpenDirectory(this)
        }
    }

    private fun chooseFileOrDirectoryToLoad(pathTarget: String? = null): File? {
        val chooser = getDirectoryChooser()
        pathTarget?.let {
            val file = File(it)
            if (file.exists()) {
                chooser.initialDirectory = file.parentFile
            }
        }
        chooser.titleProperty().bind(LanguageManager.createStringBinding("action.chooseFileOrDirectory"))
        return chooser.showDialog(ApplicationLauncher.mainStage)?.apply {
            saveLastOpenDirectory(this)
        }
    }

    fun chooseFileToSave(): File? {
        val fileChooser = getFileChooser()
        return fileChooser.showSaveDialog(ApplicationLauncher.mainStage)?.apply {
            saveLastOpenDirectory(this)
        }
    }

    private fun saveLastOpenDirectory(file: File) {
        if (file.exists()) {
            val directory = if (file.isDirectory) file else file.parentFile
            ApplicationLauncher.globalSettings.lastLoadedPath = directory.absolutePath
            GlobalSettingsLoader.save()
        }
    }

    private fun getFileChooser(): FileChooser {
        val chooser = FileChooser()
        return chooser.apply {
            ApplicationLauncher.globalSettings.lastLoadedPath?.let { path -> initialDirectory = File(path) }
            extFilter?.let { extensionFilters.add(it) }
        }
    }

    private fun getDirectoryChooser(): DirectoryChooser {
        val chooser = DirectoryChooser()
        return chooser.apply {
            ApplicationLauncher.globalSettings.lastLoadedPath?.let { path -> initialDirectory = File(path) }
        }
    }

}