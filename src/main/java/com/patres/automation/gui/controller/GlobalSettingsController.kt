package com.patres.automation.gui.controller

import com.patres.automation.Main
import javafx.animation.Interpolator
import javafx.animation.KeyFrame
import javafx.animation.KeyValue
import javafx.animation.Timeline
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.layout.StackPane
import javafx.util.Duration


class GlobalSettingsController(private val mainController: MainController) : StackPane() {

    init {
        val fxmlLoader = FXMLLoader(javaClass.getResource("/fxml/GlobalSetting.fxml"))
        fxmlLoader.setRoot(this)
        fxmlLoader.setController(this)
        fxmlLoader.load<GlobalSettingsController>()
    }


    @FXML
    fun initialize() {

    }

    @FXML
    fun closeGlobalSettings() {
        val tabPane = mainController.tabPane
        tabPane.translateXProperty().set(-Main.mainStage.scene.width)

        mainController.centerStackPane.children.add(tabPane)

        val timeline = Timeline()
        val kv = KeyValue(tabPane.translateXProperty(), 0, Interpolator.EASE_IN)
        val kf = KeyFrame(Duration.seconds(0.2), kv)
        timeline.keyFrames.add(kf)
        timeline.setOnFinished { mainController.centerStackPane.children.remove(this) }
        timeline.play()
    }

}
