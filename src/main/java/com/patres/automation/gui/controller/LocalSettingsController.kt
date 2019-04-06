package com.patres.automation.gui.controller

import com.jfoenix.controls.JFXCheckBox
import com.patres.automation.Main
import com.patres.automation.gui.controller.model.KeyboardButtonActionController
import com.patres.automation.gui.controller.model.RootSchemaGroupController
import com.patres.automation.gui.controller.model.TextFieldActionController
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

    private val stopKeysSetting = KeyboardButtonActionController(labelText = fromBundle("settings.stopKeys"))
    private val runKeysSetting = KeyboardButtonActionController(labelText = fromBundle("settings.runKeys"))
    private val enableRestCheckBox = JFXCheckBox(fromBundle("settings.enableRest"))
    private val endpointNameTextField = TextFieldActionController(labelText = fromBundle("settings.endpointName"))

    @FXML
    lateinit var mainVBox: VBox

    init {
        val fxmlLoader = FXMLLoader(javaClass.getResource("/fxml/LocalSettings.fxml"))
        fxmlLoader.setRoot(this)
        fxmlLoader.setController(this)
        fxmlLoader.resources = Main.bundle
        fxmlLoader.load<LocalSettingsController>()
        loadLocalSettings()
    }

    @FXML
    fun initialize() {
        enableRestCheckBox.selectedProperty().addListener {
            _, _, newValue ->
            endpointNameTextField.isVisible = newValue
        }
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
        settings.runKeysSetting = runKeysSetting.keyboardField.keys
        settings.enableRest = enableRestCheckBox.isSelected
        settings.endpointName = endpointNameTextField.value
    }

    private fun loadLocalSettings() {
        mainVBox.children.add(stopKeysSetting)
        mainVBox.children.add(runKeysSetting)
        mainVBox.children.add(enableRestCheckBox)
        mainVBox.children.add(endpointNameTextField)

        stopKeysSetting.keyboardField.setKeyboardButtons(settings.stopKeys)
        runKeysSetting.keyboardField.setKeyboardButtons(settings.runKeysSetting)
        enableRestCheckBox.isSelected = settings.enableRest
        endpointNameTextField.value = settings.endpointName

        endpointNameTextField.isVisible = settings.enableRest
    }

}
