package com.patres.automation.gui.dialog

import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.Label


class DialogController(private val dialogHandler: DialogHandler, private val message: String) {

    @FXML
    private lateinit var messageLabel: Label

    @FXML
    private val okButton: Button? = null

    fun initialize() {
        messageLabel.text = message
    }

    @FXML
    fun closeDialog() {
        dialogHandler.closeDialog()
    }

}
