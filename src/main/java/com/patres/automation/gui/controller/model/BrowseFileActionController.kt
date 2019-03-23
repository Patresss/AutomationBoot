package com.patres.automation.gui.controller.model

import com.jfoenix.controls.JFXButton
import com.patres.automation.action.TextActionModel
import com.patres.automation.file.FileChooser
import javafx.fxml.FXML
import javafx.scene.Node

class BrowseFileActionController(
        model: TextActionModel<out TextFieldActionController>,
        fxmlFile: String = "BrowsFileAction.fxml",
        extension: String? = null,
        extensionType: String = ""
) : TextFieldActionController(model, fxmlFile) {

    @FXML
    lateinit var browseFileButton: JFXButton

    private val loaderFile = FileChooser(extension, extensionType)

    override fun getNodesToSelect(): List<Node> = super.getNodesToSelect() + listOf(browseFileButton)

    @FXML
    override fun initialize() {
        super.initialize()
    }

    @FXML
    fun browseFile() {
        val file = loaderFile.chooseFileToLoad(value)
        if (file != null) {
            value = file.absolutePath
        }
    }


}
