package com.patres.automation.gui.dialog

import com.patres.automation.settings.LanguageManager
import javafx.application.Platform
import org.slf4j.LoggerFactory

object LogManager {

    private val logger = LoggerFactory.getLogger(LogManager::class.java)

    fun showAndLogException(e: Throwable) {
        logger.error("ApplicationException: {}", e.message, e)
        Platform.runLater {
            val errorMessage: String = LanguageManager.getLanguageString("error") + e.message
            val dialog = DialogHandler(errorMessage)
            dialog.show()
        }
    }

}
