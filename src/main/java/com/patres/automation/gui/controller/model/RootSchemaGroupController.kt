package com.patres.automation.gui.controller.model

import com.patres.automation.Main
import com.patres.automation.gui.controller.ActionBarController
import com.patres.automation.gui.custom.KeyboardButton
import com.patres.automation.model.RootSchemaGroupModel
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.control.ScrollPane
import javafx.scene.layout.BorderPane
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox


open class RootSchemaGroupController(val model: RootSchemaGroupModel): BorderPane() {

    init {
        val fxmlLoader = FXMLLoader(javaClass.getResource("/fxml/RootSchemaGroup.fxml"))
        fxmlLoader.setRoot(this)
        fxmlLoader.setController(this)
        fxmlLoader.resources = Main.bundle
        fxmlLoader.load<KeyboardButton>()
    }

    @FXML
    lateinit var insidePane: ScrollPane

    @FXML
    lateinit var actionBox: VBox

    lateinit var actionBarController: ActionBarController

    fun initialize() {
        actionBarController = ActionBarController(this)
    }

}