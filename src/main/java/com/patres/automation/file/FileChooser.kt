package com.patres.automation.file

import com.patres.automation.Main
import com.patres.automation.settings.GlobalSettingsLoader
import javafx.stage.FileChooser
import java.io.File

class FileChooser(
        extension: String? = null,
        extensionType: String = ""
) {

    private val extFilter = if (extension != null) FileChooser.ExtensionFilter("$extensionType (*.$extension)", "*.$extension") else null

    fun chooseFileToLoad(pathTarget: String? = null): File? {
        val fileChooser = getFileChooser()
        pathTarget?.let {
            val file = File(it)
            if (file.exists()) {
                fileChooser.initialDirectory = file.parentFile
            }
        }
        fileChooser.title = Main.bundle.getString("action.chooseFile")

        return fileChooser.showOpenDialog(Main.mainStage)
                ?.apply { saveLastOpenDirectory(this) }
    }

    fun chooseFileToSave(): File? {
        val fileChooser = getFileChooser()
        return fileChooser.showSaveDialog(Main.mainStage)
                ?.apply { saveLastOpenDirectory(this) }
    }

    private fun saveLastOpenDirectory(file: File) {
        if (file.exists()) {
            val directory = if (file.isDirectory) file else file.parentFile
            Main.globalSettings.lastLoadedPath = directory.absolutePath
            GlobalSettingsLoader.save()
        }
    }

    private fun getFileChooser(): FileChooser {
        return FileChooser().apply {
            Main.globalSettings.lastLoadedPath
                    ?.let { path -> initialDirectory = File(path) }
            extFilter?.let { extensionFilters.add(it) }
        }
    }

}