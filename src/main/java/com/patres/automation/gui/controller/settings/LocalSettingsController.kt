package com.patres.automation.gui.controller.settings

import com.jfoenix.controls.JFXCheckBox
import com.patres.automation.Main
import com.patres.automation.gui.controller.model.KeyboardButtonActionController
import com.patres.automation.gui.controller.model.RootSchemaGroupController
import com.patres.automation.gui.controller.model.TextFieldActionController
import com.patres.automation.settings.LocalSettings
import com.patres.automation.type.ActionBootKeyboard
import com.patres.automation.type.ActionBootTextField
import javafx.animation.Interpolator
import javafx.animation.KeyFrame
import javafx.animation.KeyValue
import javafx.animation.Timeline
import javafx.collections.ListChangeListener
import javafx.util.Duration


class LocalSettingsController(
        private val rootSchemaGroupController: RootSchemaGroupController,
        private val settings: LocalSettings
) : SettingsController("menu.settings.localSettings") {

    private val runKeysSetting = KeyboardButtonActionController(ActionBootKeyboard.RUN_KEYS_SETTINGS)
    private val stopKeysSetting = KeyboardButtonActionController(ActionBootKeyboard.STOP_KEYS_SETTINGS)
    private val enableRestCheckBox = JFXCheckBox().also { it.textProperty().bind(Main.createStringBinding("settings.enableRest")) }
    private val endpointNameTextField = TextFieldActionController(ActionBootTextField.ENDPOINT_NAME)

    init {
        loadLocalSettings()
        initChangeDetectors()
        enableRestCheckBox.selectedProperty().addListener { _, _, newValue ->
            endpointNameTextField.isVisible = newValue
        }
    }

    override fun backToPreviousWindow() {
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

    override fun saveSettings() {
        settings.runKeysSetting = runKeysSetting.keyboardField.keys
        settings.stopKeys = stopKeysSetting.keyboardField.keys
        settings.enableRest = enableRestCheckBox.isSelected
        settings.endpointName = endpointNameTextField.value
        saveButton.isDisable = true
        setMessageToSnackBar(Main.getLanguageString("message.snackbar.settingsSave"))
    }

    override fun initChangeDetectors() {
        runKeysSetting.keyboardField.keys.addListener(ListChangeListener { changeDetect() })
        stopKeysSetting.keyboardField.keys.addListener(ListChangeListener { changeDetect() })
        enableRestCheckBox.selectedProperty().addListener { _, _, _ -> changeDetect() }
        endpointNameTextField.valueText.textProperty().addListener { _, _, _ -> changeDetect() }
    }


    private fun loadLocalSettings() {
        mainVBox.children.add(runKeysSetting)
        mainVBox.children.add(stopKeysSetting)
        mainVBox.children.add(enableRestCheckBox)
        mainVBox.children.add(endpointNameTextField)

        runKeysSetting.keyboardField.setKeyboardButtons(settings.runKeysSetting)
        stopKeysSetting.keyboardField.setKeyboardButtons(settings.stopKeys)
        enableRestCheckBox.isSelected = settings.enableRest
        endpointNameTextField.value = rootSchemaGroupController.model.getEndpointName()

        endpointNameTextField.isVisible = settings.enableRest
    }


}
