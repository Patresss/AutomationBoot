package com.patres.automation.gui.controller.model

import com.jfoenix.controls.JFXButton
import com.patres.automation.file.FileChooser
import com.patres.automation.type.ActionBootBrowser
import javafx.fxml.FXML

class BrowseFileActionController(
        action: ActionBootBrowser
) : TextActionController<ActionBootBrowser>("BrowsFileAction.fxml", action) {

    @FXML
    lateinit var browseFileButton: JFXButton

    @FXML
    override fun initialize() {
        super.initialize()
    }

    @FXML
    fun browseFile() {
        val loaderFile = FileChooser(action.fileType, action.director)
        val file = loaderFile.chooseToLoad(value)
        if (file != null) {
            value = file.absolutePath
        }
    }

    override fun checkValidation() {
        action.validation?.check(value)
    }

}
