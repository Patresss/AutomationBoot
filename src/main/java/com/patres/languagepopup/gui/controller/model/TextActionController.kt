package com.patres.languagepopup.gui.controller.model

import com.jfoenix.controls.JFXButton
import com.jfoenix.controls.JFXTextField
import com.patres.languagepopup.Main
import com.patres.languagepopup.gui.controller.PointerController
import com.patres.languagepopup.model.AutomationModel
import com.patres.languagepopup.model.SchemaGroupModel
import com.patres.languagepopup.model.TextActionModel
import javafx.fxml.FXML
import javafx.scene.Node
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.layout.BorderPane
import javafx.scene.layout.Pane
import javafx.stage.Stage
import javafx.stage.StageStyle

class TextActionController : AutomationController() {

    @FXML
    lateinit var mainPane: BorderPane

    @FXML
    lateinit var valueTextField: JFXTextField

    @FXML
    lateinit var actionLabel: Label

    @FXML
    lateinit var validLabel: Label

    @FXML
    lateinit var pointButton: JFXButton


    lateinit var model: TextActionModel

    override fun getModel(): AutomationModel<out AutomationController> = model

    val value: String
        get() = valueTextField.text


    @FXML
    fun initialize() {
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

    fun getMainOutsideNode(): Node = mainPane

    fun getMainInsideNode(): Pane = mainPane
}
