package com.patres.languagepopup.gui.controller

import javafx.fxml.FXML
import javafx.scene.control.ScrollPane
import javafx.scene.layout.StackPane
import javafx.scene.layout.VBox


class RootSchemaGroupController {

    @FXML
    lateinit var rootStackPane: StackPane

    @FXML
    lateinit var insidePane: ScrollPane

    @FXML
    lateinit var actionBox: VBox

    lateinit var actionBarController: ActionBarController

    var selectedAction: SchemaGroupController? = null


    fun initialize() {
        actionBarController = ActionBarController(this)
    }

}