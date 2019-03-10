package com.patres.automation.gui.controller.model

import com.jfoenix.controls.JFXButton
import com.patres.automation.Main
import com.patres.automation.action.mouse.MouseAction
import com.patres.automation.action.mouse.MousePointAction
import com.patres.automation.gui.controller.PointerController
import com.patres.automation.model.AutomationModel
import javafx.fxml.FXML
import javafx.scene.Node
import javafx.scene.Scene
import javafx.stage.Stage
import javafx.stage.StageStyle

class MousePointActionController(
        model: MouseAction,
        fxmlFile: String = "MousePointAction.fxml"
) : TextFieldActionController(model, fxmlFile) {

    @FXML
    lateinit var pointButton: JFXButton

    override fun getNodesToSelect(): List<Node> = super.getNodesToSelect() + listOf(pointButton)

    @FXML
    override fun initialize() {
        super.initialize()
        setHandler()
    }

    private fun setHandler() {
        pointButton.setOnAction {
            Main.mainStage.isIconified = true
            showPointerStage()
        }
    }

    private fun showPointerStage() {
        val stage = Stage()
        stage.initStyle(StageStyle.TRANSPARENT)
        stage.title = "Set point"
        stage.scene = loadPointScene(stage)
        stage.isMaximized = true
        stage.show()
    }

    private fun loadPointScene(stage: Stage): Scene {
        val pointController = PointerController(stage, this)
        return pointController.scene
    }

}
