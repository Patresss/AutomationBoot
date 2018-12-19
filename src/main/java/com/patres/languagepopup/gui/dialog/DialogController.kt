package com.patres.languagepopup.gui.dialog

import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.Label

class DialogController(private val handlerDialog: HandlerDialog, private val message: String) {

    @FXML
    private lateinit var errorLabel: Label

    @FXML
    private val okButton: Button? = null

    fun initialize() {
        errorLabel.text = message
    }

    @FXML
    fun closeDialog() {
        handlerDialog.closeDialog()
    }

}
