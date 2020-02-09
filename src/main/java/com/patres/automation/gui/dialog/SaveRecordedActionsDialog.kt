package com.patres.automation.gui.dialog

import com.jfoenix.controls.JFXCheckBox
import com.jfoenix.controls.JFXDialog
import com.patres.automation.ApplicationLauncher
import com.patres.automation.gui.controller.model.RootSchemaGroupController
import com.patres.automation.gui.custom.KeyboardButton
import com.patres.automation.mapper.model.AutomationActionSerialized
import com.patres.automation.mapper.model.TextFieldActionSerialized
import com.patres.automation.settings.LanguageManager
import com.patres.automation.type.ActionBootTextField
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.layout.StackPane

class SaveRecordedActionsDialog(val actions: List<AutomationActionSerialized>, val controller: RootSchemaGroupController) : StackPane() {

    init {
        val fxmlLoader = FXMLLoader(javaClass.getResource("/fxml/dialog/SaveRecordedActions.fxml"))
        fxmlLoader.setRoot(this)
        fxmlLoader.setController(this)
        fxmlLoader.resources = LanguageManager.getBundle()
        fxmlLoader.load<KeyboardButton>()
    }

    @FXML
    lateinit var removeDelayCheckBox: JFXCheckBox

    @FXML
    lateinit var removeLastActionCheckBox: JFXCheckBox

    private val jfxDialog = JFXDialog(ApplicationLauncher.mainPane, this, JFXDialog.DialogTransition.CENTER)

    fun showDialog() {
        jfxDialog.show()
    }

    private fun closeDialog() {
        jfxDialog.close()
    }

    @FXML
    fun save() {
        val controllers = actions
                .filterNot { removeDelayCheckBox.isSelected && it is TextFieldActionSerialized && it.actionType == ActionBootTextField.DELAY }
                .filterNot { removeLastActionCheckBox.isSelected && actions.indexOf(it) + 1 == actions.size}
                .map { it.serializedToController() }
        controllers.forEach { controller.addActionBlocks(it) }

        closeDialog()
    }

    @FXML
    fun doNotSave() {
        closeDialog()
    }

}
