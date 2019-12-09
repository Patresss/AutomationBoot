package com.patres.automation.gui.dialog

import javafx.application.Platform
import org.slf4j.LoggerFactory

object LogManager {

    private val logger = LoggerFactory.getLogger(LogManager::class.java)


    fun showAndLogException(e: Exception) {
        logger.error("ApplicationException: {}", e.message, e)
        Platform.runLater {
            val dialog = ExceptionHandlerDialog(e)
            dialog.show()
        }
    }

}
