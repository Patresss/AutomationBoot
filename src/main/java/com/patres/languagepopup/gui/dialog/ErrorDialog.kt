package com.patres.languagepopup.gui.dialog

import javafx.scene.control.Alert
import javafx.scene.control.Label
import javafx.scene.control.TextArea
import javafx.scene.layout.GridPane
import javafx.scene.layout.Priority
import org.apache.commons.lang3.exception.ExceptionUtils
import java.io.PrintWriter
import java.io.StringWriter

class ErrorDialog private constructor() {

    companion object {
        fun showAndWait(exception: Throwable) {
            val alert = Alert(Alert.AlertType.ERROR)
            alert.title = "Exception Dialog"
            alert.headerText = "Error"
            alert.contentText = ExceptionUtils.getRootCauseMessage(exception)
            exception.cause

            val sw = StringWriter()
            val pw = PrintWriter(sw)
            exception.printStackTrace(pw)
            val exceptionText = sw.toString()

            val label = Label("The exception stacktrace was:")

            val textArea = TextArea(exceptionText)
            textArea.isEditable = false
            textArea.isWrapText = true

            textArea.maxWidth = java.lang.Double.MAX_VALUE
            textArea.maxHeight = java.lang.Double.MAX_VALUE
            GridPane.setVgrow(textArea, Priority.ALWAYS)
            GridPane.setHgrow(textArea, Priority.ALWAYS)

            val expContent = GridPane()
            expContent.maxWidth = java.lang.Double.MAX_VALUE
            expContent.add(label, 0, 0)
            expContent.add(textArea, 0, 1)

            alert.dialogPane.expandableContent = expContent
            alert.showAndWait()
        }
    }


}