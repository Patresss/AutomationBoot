package com.patres.automation.gui.controller

import com.patres.automation.Main
import com.patres.automation.gui.controller.model.KeyboardButtonActionController
import com.patres.automation.settings.GlobalSettingsLoader
import com.patres.automation.util.fromBundle
import javafx.animation.Interpolator
import javafx.animation.KeyFrame
import javafx.animation.KeyValue
import javafx.animation.Timeline
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.layout.BorderPane
import javafx.scene.layout.VBox
import javafx.util.Duration


class GlobalSettingsController(private val mainController: MainController) : BorderPane() {


    private val stopKeysSetting = KeyboardButtonActionController(labelText = fromBundle("settings.stopKeys"))

    @FXML
    lateinit var mainVBox: VBox

    init {
        val fxmlLoader = FXMLLoader(javaClass.getResource("/fxml/GlobalSetting.fxml"))
        fxmlLoader.setRoot(this)
        fxmlLoader.setController(this)
        fxmlLoader.resources = Main.bundle
        fxmlLoader.load<GlobalSettingsController>()

        mainVBox.children.add(stopKeysSetting)

        loadGlobalSettings()
    }

    @FXML
    fun closeGlobalSettings() {
        val tabPane = mainController.tabPane
        tabPane.translateXProperty().set(-Main.mainStage.scene.width)

        mainController.centerStackPane.children.add(tabPane)

        val timeline = Timeline()
        val kv = KeyValue(tabPane.translateXProperty(), 0, Interpolator.EASE_IN)
        val kf = KeyFrame(Duration.seconds(0.1), kv)
        timeline.keyFrames.add(kf)
        timeline.setOnFinished { mainController.centerStackPane.children.remove(this) }
        timeline.play()
    }

    @FXML
    fun saveGlobalSettings() {
        Main.globalSettings.stopKeys = stopKeysSetting.keyboardField.keys
        GlobalSettingsLoader.save(Main.globalSettings)
    }

    fun loadGlobalSettings() {
        Main.globalSettings
        stopKeysSetting.keyboardField.setKeyboardButtons(Main.globalSettings.stopKeys)
    }

}
