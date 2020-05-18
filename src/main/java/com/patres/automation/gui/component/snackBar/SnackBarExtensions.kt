package com.patres.automation.gui.component.snackBar

import com.jfoenix.controls.JFXSnackbar
import com.patres.automation.settings.LanguageManager
import javafx.scene.control.Label
import javafx.util.Duration

var DEFAULT_DURATION: Duration = Duration.seconds(2.0)

fun JFXSnackbar.addMessage(type: SnackBarType = SnackBarType.INFO, message: String, duration: Duration = DEFAULT_DURATION) {
    val currentContent = this.currentEvent?.content
    if (!(currentContent is Label && currentContent.text == message)) {
        val snackBarEvent = SnackBarFactory.create(message, type, duration)
        this.enqueue(snackBarEvent)
    }
}

fun JFXSnackbar.addMessageLanguage(type: SnackBarType = SnackBarType.INFO, messageKey: String, vararg args: Any) {
    val message = LanguageManager.getLanguageString(messageKey, *args)
    val currentContent = this.currentEvent?.content
    if (!(currentContent is Label && currentContent.text == message)) {
        val snackBarEvent = SnackBarFactory.create(message, type, DEFAULT_DURATION)
        this.enqueue(snackBarEvent)
    }
}

fun JFXSnackbar.addMessageLanguageWhenIsLoaded(loaded: Boolean, type: SnackBarType = SnackBarType.INFO, messageKey: String, vararg args: Any) {
    if (loaded) {
        addMessageLanguage(type, messageKey, *args)
    }
}