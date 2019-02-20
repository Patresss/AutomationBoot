package com.patres.languagepopup.gui.dialog

import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.Label

class DialogController(private val exceptionHandlerDialog: ExceptionHandlerDialog, private val exception: Exception) {

    @FXML
    private lateinit var errorLabel: Label

    @FXML
    private val okButton: Button? = null

    fun initialize() {
        errorLabel.text = exception.message
    }

    @FXML
    fun closeDialog() {
        exceptionHandlerDialog.closeDialog()
    }

}
