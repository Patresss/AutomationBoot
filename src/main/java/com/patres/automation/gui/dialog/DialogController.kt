package com.patres.automation.gui.dialog

import com.patres.automation.Main
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.Label


class DialogController(private val exceptionHandlerDialog: ExceptionHandlerDialog, private val exception: Exception) {

    @FXML
    private lateinit var errorLabel: Label

    @FXML
    private val okButton: Button? = null

    fun initialize() {
        val error: String = Main.getLanguageString("error")
        errorLabel.text = "$error: ${exception.message}"
    }

    @FXML
    fun closeDialog() {
        exceptionHandlerDialog.closeDialog()
    }

}
