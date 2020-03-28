package com.patres.automation.gui.dialog

import com.patres.automation.settings.LanguageManager
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.Label


class DialogController(private val exceptionHandlerDialog: ExceptionHandlerDialog, private val exception: Throwable) {

    @FXML
    private lateinit var errorLabel: Label

    @FXML
    private val okButton: Button? = null

    fun initialize() {
        val error: String = LanguageManager.getLanguageString("error")
        errorLabel.text = "$error: ${exception.message}"
    }

    @FXML
    fun closeDialog() {
        exceptionHandlerDialog.closeDialog()
    }

}
