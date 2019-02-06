package com.patres.languagepopup.gui.controller.model

import com.patres.languagepopup.gui.controller.ActionBarController
import javafx.fxml.FXML
import javafx.scene.control.ScrollPane
import javafx.scene.layout.StackPane
import javafx.scene.layout.VBox


open class RootSchemaGroupController {

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