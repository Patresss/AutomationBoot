package com.patres.automation.gui.component.snackBar

import com.jfoenix.controls.JFXSnackbar
import javafx.scene.control.Label
import javafx.util.Duration

object SnackBarFactory {

    fun create(message: String, type: SnackBarType = SnackBarType.INFO, duration: Duration): JFXSnackbar.SnackbarEvent {
        return if (type.pseudoClass == null) {
            JFXSnackbar.SnackbarEvent(Label(message), duration)
        } else {
            JFXSnackbar.SnackbarEvent(Label(message), duration, type.pseudoClass)
        }
    }

}