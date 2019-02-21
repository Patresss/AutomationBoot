package com.patres.languagepopup.gui.controller

import com.jfoenix.controls.JFXSnackbar
import com.jfoenix.controls.JFXSnackbar.SnackbarEvent
import com.jfoenix.controls.JFXTabPane
import com.patres.languagepopup.Main
import com.patres.languagepopup.util.LoaderFactory
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.control.TabPane
import javafx.scene.layout.StackPane


class MainController {

    @FXML
    var root: StackPane? = null

    @FXML
    var tabPane: JFXTabPane? = null

    @FXML
    lateinit var fichesTab: StackPane

    @FXML
    lateinit var settingsTab: StackPane

    private lateinit var snackBar: JFXSnackbar

    fun initialize() {
        snackBar = JFXSnackbar(root)
        tabPane?.tabClosingPolicy = TabPane.TabClosingPolicy.ALL_TABS
        addNewSchema()
    }

    @FXML
    fun addNewSchema() {
        LoaderFactory.createRootSchemaGroup(tabPane)
    }

    fun setMessageToSnackBar(message: String) {
        snackBar.fireEvent(
                SnackbarEvent(message, "X",
                        2000,
                        false,
                        EventHandler { snackBar.close() }))
    }

}
