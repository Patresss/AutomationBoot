package com.patres.automation.gui.controller.saveBackScreen.activeSchema

import com.jfoenix.controls.JFXButton
import com.patres.automation.action.RootSchemaGroupModel
import com.patres.automation.mapper.model.RootSchemaGroupSerialized
import com.patres.automation.settings.LanguageManager
import javafx.application.Platform
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.control.Label
import javafx.scene.control.Tooltip
import javafx.scene.layout.StackPane


class ActiveSchemaItemController(
        private val activeSchemasController: ActiveSchemasController,
        val model: RootSchemaGroupModel,
        val serialized: RootSchemaGroupSerialized
) : StackPane() {

    @FXML
    lateinit var activeSchemaLabel: Label

    @FXML
    lateinit var runButton: JFXButton

    @FXML
    lateinit var stopButton: JFXButton

    @FXML
    lateinit var nameTooltip: Tooltip

    init {
        val fxmlLoader = FXMLLoader(javaClass.getResource("/fxml/ActiveActionItem.fxml"))
        fxmlLoader.setRoot(this)
        fxmlLoader.setController(this)
        fxmlLoader.resources = LanguageManager.getBundle()
        fxmlLoader.load<ActiveSchemaItemController>()

        activeSchemaLabel.text = "• ${model.actionRunner.rootFiles.getName()}"
        nameTooltip.text = model.actionRunner.rootFiles.currentFile.absolutePath
    }

    fun initialize() {
        manageRunStopButtons(model.actionRunner.isRunning())
        model.actionRunner.automationRunningProperty.addListener { _, _, isRunning -> manageRunStopButtons(isRunning) }
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
        activeSchemasController.toRemoveSchema.add(this)

    }

    @FXML
    fun runAction() {
        model.runAutomation()
    }

    @FXML
    fun stopAction() {
        model.stopAutomation()
    }

    private fun manageRunStopButtons(isRunning: Boolean) {
        Platform.runLater {
            runButton.isVisible = !isRunning
            stopButton.isVisible = isRunning
        }
    }

}
