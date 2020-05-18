package com.patres.automation.gui.controller

import com.patres.automation.settings.LanguageManager
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.control.Label
import javafx.scene.layout.StackPane

class EmptyTabController(val mainController: MainController) : StackPane() {

    @FXML
    lateinit var mainLabel: Label

    @FXML
    lateinit var secondaryLabel: Label

    init {
        val fxmlLoader = FXMLLoader(javaClass.getResource("/fxml/EmptyTab.fxml"))
        fxmlLoader.setRoot(this)
        fxmlLoader.setController(this)
        fxmlLoader.resources = LanguageManager.getBundle()
        fxmlLoader.load<EmptyTabController>()
    }

    @FXML
    fun initialize() {
        mainLabel.textProperty().bind(LanguageManager.createStringBinding("application.name"))
        secondaryLabel.textProperty().bind(LanguageManager.createStringBinding("application.ready"))
    }

    @FXML
    fun createNewTab() {
        mainController.createNewRootSchema()
    }

}
