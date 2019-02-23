package com.patres.languagepopup.gui.controller.model

import com.jfoenix.controls.JFXTextField
import javafx.fxml.FXML
import javafx.scene.control.Label

open class TextFieldActionController : LabelActionController() {

    @FXML
    lateinit var valueTextField: JFXTextField

    @FXML
    lateinit var validLabel: Label

    val value: String
        get() = valueTextField.text

}
