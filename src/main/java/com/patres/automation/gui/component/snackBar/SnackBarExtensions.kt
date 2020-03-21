package com.patres.automation.gui.component.snackBar

import com.jfoenix.controls.JFXSnackbar
import com.patres.automation.settings.LanguageManager
import javafx.scene.control.Label

fun JFXSnackbar.addMessageLanguage(type: SnackBarType = SnackBarType.INFO, messageKey: String, vararg args: Any) {
    val message = LanguageManager.getLanguageString(messageKey, *args)
    val currentContent = this.currentEvent?.content
    if (!(currentContent is Label && currentContent.text == message)) {
        val snackBarEvent = SnackBarFactory.create(message, type)
        this.enqueue(snackBarEvent)
    }
}

fun JFXSnackbar.addMessageLanguageWhenIsLoaded(loaded: Boolean, type: SnackBarType = SnackBarType.INFO, messageKey: String, vararg args: Any) {
    if (loaded) {
        addMessageLanguage(type, messageKey, *args)
    }
}