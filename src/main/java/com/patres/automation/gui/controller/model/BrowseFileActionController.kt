package com.patres.automation.gui.controller.model

import com.jfoenix.controls.JFXButton
import com.patres.automation.action.AbstractAction
import com.patres.automation.action.script.OpenFileOrDirectoryAction
import com.patres.automation.action.script.WindowsRunAndWaitScriptAction
import com.patres.automation.action.script.WindowsRunScriptAction
import com.patres.automation.action.text.PasteTextFromFileAction
import com.patres.automation.action.text.TypeTextFromFileAction
import com.patres.automation.file.FileChooser
import com.patres.automation.model.RootSchemaGroupModel
import com.patres.automation.type.ActionBootBrowser
import javafx.fxml.FXML
import javafx.scene.Node

class BrowseFileActionController(
        root: RootSchemaGroupModel,
        parent: SchemaGroupController,
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
        return when (action) {
            ActionBootBrowser.PASTE_TEXT_FROM_FILE -> PasteTextFromFileAction(value)
            ActionBootBrowser.TYPE_TEXT_FROM_FILE -> TypeTextFromFileAction(value, root.automationRunningProperty)
            ActionBootBrowser.OPEN_FILE_OR_DIRECTORY -> OpenFileOrDirectoryAction(value)
            ActionBootBrowser.WINDOWS_SCRIPT_RUN -> WindowsRunScriptAction(value)
            ActionBootBrowser.WINDOWS_SCRIPT_RUN_AND_WAITE -> WindowsRunAndWaitScriptAction(value)
        }
    }

}
