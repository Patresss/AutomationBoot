package com.patres.automation.gui.controller.settings

import com.jfoenix.controls.JFXButton
import com.jfoenix.controls.JFXDialog
import com.jfoenix.controls.JFXSnackbar
import com.patres.automation.Main
import com.patres.automation.gui.controller.MainController
import com.patres.automation.gui.dialog.SaveSettingsDialog
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.control.Label
import javafx.scene.layout.BorderPane
import javafx.scene.layout.VBox


abstract class SettingsController(val bundleName: String) : BorderPane() {

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
        val fxmlLoader = FXMLLoader(javaClass.getResource("/fxml/Settings.fxml"))
        fxmlLoader.setRoot(this)
        fxmlLoader.setController(this)
        fxmlLoader.resources = Main.getBundle()
        fxmlLoader.load<SettingsController>()

        saveButton.isDisable = true
        snackBar = JFXSnackbar(this)
        titleLabel.textProperty().bind(Main.createStringBinding(bundleName))
    }

    @FXML
    open fun initialize() {

    }

    @FXML
    fun closeSettings() {
        if (saveButton.isDisable) {
            backToPreviousWindow()
        } else {
            val saveDialogPane = SaveSettingsDialog(this)
            val jfxDialog = JFXDialog(Main.mainPane, saveDialogPane, JFXDialog.DialogTransition.CENTER)
            saveDialogPane.dialogKeeper = jfxDialog
            jfxDialog.show()
        }
    }

    fun changeDetect() {
        saveButton.isDisable = false
    }

    fun setMessageToSnackBar(message: String) {
        snackBar.fireEvent(JFXSnackbar.SnackbarEvent(message, "X", MainController.MESSAGE_SNACKBAR_TIMEOUT, false, EventHandler { snackBar.close() }))
    }

    abstract fun initChangeDetectors()
    abstract fun saveSettings()
    abstract fun backToPreviousWindow()

}
