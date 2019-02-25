package com.patres.automation.gui.controller.model

import com.patres.automation.gui.controller.ActionBarController
import com.patres.automation.model.RootSchemaGroupModel
import javafx.fxml.FXML
import javafx.scene.control.ScrollPane
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox


open class RootSchemaGroupController {

    @FXML
    lateinit var rootStackPane: Pane

    @FXML
    lateinit var insidePane: ScrollPane

    @FXML
    lateinit var actionBox: VBox

    lateinit var actionBarController: ActionBarController

    lateinit var model: RootSchemaGroupModel

    fun initialize() {
        actionBarController = ActionBarController(this)
    }

}