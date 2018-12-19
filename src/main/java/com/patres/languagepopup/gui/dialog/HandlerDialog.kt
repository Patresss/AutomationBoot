package com.patres.languagepopup.gui.dialog

import com.jfoenix.controls.JFXDialog
import com.jfoenix.controls.JFXDialog.DialogTransition
import com.patres.languagepopup.Main
import javafx.fxml.FXMLLoader
import javafx.scene.layout.Region
import org.slf4j.LoggerFactory
import java.io.IOException
import java.util.*


class HandlerDialog(private val message: String) {

    companion object {
        private val LOGGER = LoggerFactory.getLogger(HandlerDialog::class.java)
    }

    private var dialog: JFXDialog

    init {
        val dialogPane = Main.mainController.root
        dialog = JFXDialog(dialogPane, getDialogContent(), DialogTransition.CENTER)
    }

    fun show() = dialog.show()

    fun closeDialog() = dialog.close()

    private fun getDialogContent(): Region? {
        var content: Region? = null
        try {
            val loader = FXMLLoader()
            loader.location = javaClass.getResource("/fxml/dialog/ExceptionHandlerDialog.fxml")
            loader.resources = getDialogBundle()
            loader.setController(DialogController(this, message))
            content = loader.load<Region>()
        } catch (e: IOException) {
            LOGGER.error("I/O Exception", e)
        }

        return content
    }

    private fun getDialogBundle(): ResourceBundle = Main.bundle

}
