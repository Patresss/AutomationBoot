package com.patres.automation.gui.dialog

import com.patres.automation.settings.LanguageManager
import javafx.application.Platform
import org.slf4j.LoggerFactory

object LogManager {

    private val logger = LoggerFactory.getLogger(LogManager::class.java)

    fun showAndLogException(e: Throwable) {
        showAndLogException(e.message, e)
    }

    fun showAndLogException(message: String?, e: Throwable) {
        logger.error("ApplicationException: {}", message, e)
        Platform.runLater {
            val errorMessage = "${LanguageManager.getLanguageString("error")}: $message"
            val dialog = DialogHandler(errorMessage)
            dialog.show()
        }
    }

}
