package com.patres.automation.gui.controller.model

import com.jfoenix.controls.JFXButton
import com.patres.automation.action.AbstractAction
import com.patres.automation.file.FileChooser
import com.patres.automation.mapper.BrowserActionMapper
import com.patres.automation.mapper.model.BrowserActionSerialized
import com.patres.automation.model.RootSchemaGroupModel
import com.patres.automation.type.ActionBootBrowser
import javafx.fxml.FXML
import javafx.scene.Node

class BrowseFileActionController(
        root: RootSchemaGroupModel,
        parent: SchemaGroupController?,
        action: ActionBootBrowser
) : TextActionController<ActionBootBrowser>("BrowsFileAction.fxml", root, parent, action) {

    @FXML
    lateinit var browseFileButton: JFXButton

    private val loaderFile = FileChooser(action.fileType)

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

    override fun toModel(): AbstractAction {
        action.validation?.check(value)
        return BrowserActionMapper.controllerToModel(this)
    }

    override fun toSerialized(): BrowserActionSerialized {
        return BrowserActionMapper.controllerToSerialized(this)
    }

}
