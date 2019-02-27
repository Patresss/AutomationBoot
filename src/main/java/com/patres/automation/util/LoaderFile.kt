package com.patres.automation.util

import com.patres.automation.Main
import javafx.stage.FileChooser
import java.io.File

object LoaderFile {

    const val extension = ".abJson"
    private val extFilter = FileChooser.ExtensionFilter("Automation boot (*$extension)", "*$extension")

    fun chooseFileToLoad(): File? {
        val fileChooser = FileChooser()
        fileChooser.extensionFilters.add(extFilter)
        fileChooser.title = Main.bundle.getString("action.chooseFile")
        return fileChooser.showOpenDialog(Main.mainStage)
    }

    fun chooseFileToSave(): File? {
        val fileChooser = FileChooser()
        fileChooser.extensionFilters.add(extFilter)
        return fileChooser.showSaveDialog(Main.mainStage)
    }

}