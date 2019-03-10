package com.patres.automation.gui.dialog

import com.patres.automation.Main
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.Label


class DialogController(private val exceptionHandlerDialog: ExceptionHandlerDialog, private val exception: Exception) {

    companion object {
        val error: String = Main.bundle.getString("error")
    }

    @FXML
    private lateinit var errorLabel: Label

    @FXML
    private val okButton: Button? = null

    fun initialize() {
        errorLabel.text = "$error: ${exception.message}"
    }

    @FXML
    fun closeDialog() {
        exceptionHandlerDialog.closeDialog()
    }

}
