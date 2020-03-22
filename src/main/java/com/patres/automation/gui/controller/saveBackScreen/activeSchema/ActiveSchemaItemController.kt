package com.patres.automation.gui.controller.saveBackScreen.activeSchema

import com.jfoenix.controls.JFXButton
import com.patres.automation.settings.LanguageManager
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.control.Label
import javafx.scene.control.Tooltip
import javafx.scene.layout.StackPane


class ActiveSchemaItemController(
        private val activeSchemasController: ActiveSchemasController,
        val name: String,
        val path: String) : StackPane() {

    @FXML
    lateinit var activeSchemaLabel: Label

    @FXML
    lateinit var editButton: JFXButton

    @FXML
    lateinit var closeButton: JFXButton

    @FXML
    lateinit var nameTooltip: Tooltip


    init {
        val fxmlLoader = FXMLLoader(javaClass.getResource("/fxml/ActiveActionItem.fxml"))
        fxmlLoader.setRoot(this)
        fxmlLoader.setController(this)
        fxmlLoader.resources = LanguageManager.getBundle()
        fxmlLoader.load<ActiveSchemaItemController>()

        activeSchemaLabel.text = "• $name"
        nameTooltip.text = path
    }


    @FXML
    fun editActiveSchema() {
        activeSchemasController.changeDetect()
        activeSchemasController.removeActiveSchemaFromUiList(this)
        activeSchemasController.toEditSchema.add(this)
    }

    @FXML
    fun closeActiveSchema() {
        activeSchemasController.changeDetect()
        activeSchemasController.removeActiveSchemaFromUiList(this)
    }

}
