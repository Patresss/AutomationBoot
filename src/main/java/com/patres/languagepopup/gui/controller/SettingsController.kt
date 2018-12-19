package com.patres.languagepopup.gui.controller

import com.jfoenix.controls.JFXSlider
import com.jfoenix.controls.JFXTextField
import com.patres.languagepopup.Main
import com.patres.languagepopup.util.setIntegerFilter
import javafx.fxml.FXML
import java.awt.Point


class SettingsController {

    @FXML
    private lateinit var minutesTextField: JFXTextField

    @FXML
    private lateinit var positionXTextField: JFXTextField

    @FXML
    private lateinit var positionYTextField: JFXTextField

    @FXML
    private lateinit var fontSizeTextField: JFXTextField

    @FXML
    private lateinit var opacitySlider: JFXSlider

    var mainController: MainController? = null

    fun initialize() {
        reload()
        minutesTextField.setIntegerFilter()
        positionXTextField.setIntegerFilter()
        positionYTextField.setIntegerFilter()
        fontSizeTextField.setIntegerFilter()
    }

    @FXML
    fun reload() {
        mainController?.setMessageToSnackBar(Main.bundle.getString("action.reloaded"))
    }

    @FXML
    fun save() {
        val minutes = minutesTextField.text
        val minutesInt = minutes?.toIntOrNull()
        if (minutesInt == null) {
            mainController?.setMessageToSnackBar(Main.bundle.getString("error.mustBeNumber"))
            return
        }

        val positionX = positionXTextField.text
        val positionXInt = positionX?.toIntOrNull()
        if (positionXInt == null) {
            mainController?.setMessageToSnackBar(Main.bundle.getString("error.mustBeNumber"))
            return
        }

        val positionY = positionYTextField.text
        val positionYInt = positionY?.toIntOrNull()
        if (positionYInt == null) {
            mainController?.setMessageToSnackBar(Main.bundle.getString("error.mustBeNumber"))
            return
        }

        val fontSize = fontSizeTextField.text
        val fontSizeInt = fontSize?.toIntOrNull()
        if (fontSizeInt == null) {
            mainController?.setMessageToSnackBar(Main.bundle.getString("error.mustBeNumber"))
            return
        }

        val opacity = opacitySlider.value

        mainController?.setMessageToSnackBar(Main.bundle.getString("action.saved"))
    }

}
