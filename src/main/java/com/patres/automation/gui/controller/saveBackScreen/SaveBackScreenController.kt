package com.patres.automation.gui.controller.saveBackScreen

import com.jfoenix.controls.JFXButton
import com.jfoenix.controls.JFXDialog
import com.jfoenix.controls.JFXSnackbar
import com.patres.automation.ApplicationLauncher
import com.patres.automation.gui.dialog.LogManager
import com.patres.automation.gui.dialog.SaveSettingsDialog
import com.patres.automation.settings.LanguageManager
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.control.Label
import javafx.scene.layout.BorderPane
import javafx.scene.layout.VBox


abstract class SaveBackScreenController(val bundleName: String) : BorderPane() {

    @FXML
    lateinit var mainVBox: VBox

    @FXML
    lateinit var backButton: JFXButton

    @FXML
    lateinit var saveButton: JFXButton

    @FXML
    lateinit var titleLabel: Label

    private var snackBar: JFXSnackbar

    init {
        val fxmlLoader = FXMLLoader(javaClass.getResource("/fxml/SaveBackScreen.fxml"))
        fxmlLoader.setRoot(this)
        fxmlLoader.setController(this)
        fxmlLoader.resources = LanguageManager.getBundle()
        fxmlLoader.load<SaveBackScreenController>()

        saveButton.isDisable = true
        snackBar = JFXSnackbar(this)
        titleLabel.textProperty().bind(LanguageManager.createStringBinding(bundleName))
        backButton.textProperty().bind(LanguageManager.createStringBinding("button.back"))
        saveButton.textProperty().bind(LanguageManager.createStringBinding("menu.save"))
    }

    @FXML
    open fun initialize() {

    }

    @FXML
    fun save() {
        try {
            saveChanges()
            saveButton.isDisable = true
        } catch (e: Exception) {
            LogManager.showAndLogException(e)
        }
    }

    @FXML
    fun close() {
        if (saveButton.isDisable) {
            backToPreviousWindow()
        } else {
            val saveDialogPane = SaveSettingsDialog(this)
            val jfxDialog = JFXDialog(ApplicationLauncher.mainPane, saveDialogPane, JFXDialog.DialogTransition.CENTER)
            saveDialogPane.dialogKeeper = jfxDialog
            jfxDialog.show()
        }
    }

    fun changeDetect() {
        saveButton.isDisable = false
    }

    fun setMessageToSnackBar(message: String) {
        snackBar.fireEvent(JFXSnackbar.SnackbarEvent(Label(message)))
    }

    abstract fun initChangeDetectors()
    abstract fun backToPreviousWindow()
    abstract fun reloadSettingsValue()
    abstract fun saveChanges()

}
