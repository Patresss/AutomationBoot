package com.patres.automation.gui.controller

import com.patres.automation.Main
import com.patres.automation.gui.controller.model.KeyboardButtonActionController
import com.patres.automation.gui.controller.model.RootSchemaGroupController
import com.patres.automation.settings.GlobalSettingsLoader
import com.patres.automation.settings.LocalSettings
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


class LocalSettingsController(
        private val rootSchemaGroupController: RootSchemaGroupController,
        private val settings: LocalSettings
) : BorderPane() {


    private val stopKeysSetting = KeyboardButtonActionController(labelText = fromBundle("settings.stopKey"))

    @FXML
    lateinit var mainVBox: VBox

    init {
        val fxmlLoader = FXMLLoader(javaClass.getResource("/fxml/LocalSettings.fxml"))
        fxmlLoader.setRoot(this)
        fxmlLoader.setController(this)
        fxmlLoader.resources = Main.bundle
        fxmlLoader.load<LocalSettingsController>()

        mainVBox.children.add(stopKeysSetting)

        loadLocalSettings()
    }


    @FXML
    fun initialize() {

    }

    @FXML
    fun closeLocalSettings() {
        val rootBorderPane = rootSchemaGroupController.rootBorderPane
        rootBorderPane.translateXProperty().set(-Main.mainStage.scene.width)

        rootSchemaGroupController.children.add(rootBorderPane)

        val timeline = Timeline()
        val kv = KeyValue(rootBorderPane.translateXProperty(), 0, Interpolator.EASE_IN)
        val kf = KeyFrame(Duration.seconds(0.1), kv)
        timeline.keyFrames.add(kf)
        timeline.setOnFinished { rootSchemaGroupController.children.remove(this) }
        timeline.play()
    }

    @FXML
    fun saveLocalSettings() {
        settings.stopKeys = stopKeysSetting.keyboardField.keys
    }

    private fun loadLocalSettings() {
        stopKeysSetting.keyboardField.setKeyboardButtons(settings.stopKeys)
    }

}
