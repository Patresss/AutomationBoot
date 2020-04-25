package com.patres.automation.gui.dialog

import com.jfoenix.controls.JFXDialog
import com.patres.automation.gui.controller.saveBackScreen.SaveBackScreenController
import com.patres.automation.gui.custom.KeyboardButton
import com.patres.automation.settings.LanguageManager
import com.patres.automation.util.extension.fromBundle
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.control.Label
import javafx.scene.layout.StackPane

class SaveSettingsDialog(private val settingsController: SaveBackScreenController) : StackPane() {

    init {
        val fxmlLoader = FXMLLoader(javaClass.getResource("/fxml/dialog/SaveDialog.fxml"))
        fxmlLoader.setRoot(this)
        fxmlLoader.setController(this)
        fxmlLoader.resources = LanguageManager.getBundle()
        fxmlLoader.load<KeyboardButton>()
    }

    @FXML
    lateinit var label: Label

    var dialogKeeper: JFXDialog? = null

    @FXML
    fun initialize() {
        label.text = fromBundle("dialog.save.saveSettings")
    }

    @FXML
    fun save() {
        try {
            settingsController.saveChanges()
            settingsController.backToPreviousWindow()
        } catch (e: Exception) {
            LogManager.showAndLogException(e)
        } finally {
            dialogKeeper?.close()
        }
    }

    @FXML
    fun doNotSave() {
        settingsController.backToPreviousWindow()
        dialogKeeper?.close()
    }

    @FXML
    fun cancel() {
        dialogKeeper?.close()
    }

}
