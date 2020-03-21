package com.patres.automation.gui.component.snackBar

import com.jfoenix.controls.JFXSnackbar
import javafx.scene.control.Label

object SnackBarFactory {

    fun create(message: String, type: SnackBarType = SnackBarType.INFO): JFXSnackbar.SnackbarEvent {
        return if (type.pseudoClass == null) {
            JFXSnackbar.SnackbarEvent(Label(message))
        } else {
            JFXSnackbar.SnackbarEvent(Label(message), type.pseudoClass)
        }
    }

}