package com.patres.languagepopup.gui.dialog

import com.jfoenix.controls.JFXDialog
import com.jfoenix.controls.JFXDialog.DialogTransition
import com.patres.languagepopup.Main
import javafx.fxml.FXMLLoader
import javafx.scene.layout.Region
import org.slf4j.LoggerFactory
import java.io.IOException
import java.net.URL
import java.util.*

class ExceptionHandlerDialog(private val exception: Exception) {

    private var dialog: JFXDialog

    private val dialogContent: Region?
        get() {
            var content: Region? = null
            try {
                val loader = FXMLLoader()
                loader.location = javaClass.getResource("/fxml/dialog/ExceptionHandlerDialog.fxml")
                loader.setController(DialogController(this, exception))
                val bundle = ResourceBundle.getBundle("language/language", Locale.ENGLISH)
                loader.resources = bundle
                content = loader.load<Region>()
            } catch (e: IOException) {
                LOGGER.error("I/O Exception", e)
            }

            return content
        }

    init {
        val dialogPane = Main.mainPane
        dialog = JFXDialog(dialogPane, dialogContent, DialogTransition.CENTER)
    }

    fun show() {
        dialog.show()
    }

    fun closeDialog() {
        dialog.close()
    }

    companion object {

        private val LOGGER = LoggerFactory.getLogger(ExceptionHandlerDialog::class.java)
    }

}
