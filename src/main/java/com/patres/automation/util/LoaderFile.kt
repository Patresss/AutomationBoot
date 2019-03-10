package com.patres.automation.util

import com.patres.automation.Main
import javafx.stage.FileChooser
import java.io.File

class LoaderFile(
        extension: String? = null
) {

    companion object {
        const val AUTOMATION_BOOT_EXTENSION = ".ab"
    }

    private val extFilter = if (extension != null ) FileChooser.ExtensionFilter("Automation boot (*$extension)", "*$extension") else null

    fun chooseFileToLoad(pathTarget: String? = null): File? {
        val fileChooser = FileChooser()
        extFilter?.let { fileChooser.extensionFilters.add(it)  }
        pathTarget?.let {
            val file = File(it)
            if (file.exists()) {
                fileChooser.initialDirectory = file.parentFile
            }
        }
        fileChooser.title = Main.bundle.getString("action.chooseFile")
        return fileChooser.showOpenDialog(Main.mainStage)
    }

    fun chooseFileToSave(pathTarget: String? = null): File? {
        val fileChooser = FileChooser()
        extFilter?.let { fileChooser.extensionFilters.add(it)  }
        pathTarget?.let {
            val file = File(it)
            if (file.exists()) {
                fileChooser.initialDirectory = file.parentFile
            }
        }
        return fileChooser.showSaveDialog(Main.mainStage)
    }

}