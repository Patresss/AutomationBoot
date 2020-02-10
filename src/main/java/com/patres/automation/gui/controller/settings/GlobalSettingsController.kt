package com.patres.automation.gui.controller.settings

import com.patres.automation.ApplicationLauncher
import com.patres.automation.gui.animation.SliderAnimation
import com.patres.automation.gui.controller.MainController
import com.patres.automation.gui.controller.model.KeyboardButtonActionController
import com.patres.automation.settings.GlobalSettingsLoader
import com.patres.automation.type.ActionBootKeyboard
import com.patres.automation.type.ChooseLanguageActionBootComboBox
import com.patres.automation.util.fromBundle
import javafx.collections.ListChangeListener


class GlobalSettingsController(private val mainController: MainController) : SettingsController("menu.settings.globalSettings") {

    private val stopKeysSetting = KeyboardButtonActionController(ActionBootKeyboard.STOP_KEYS_SETTINGS)
    private val startRecordKeysSettings = KeyboardButtonActionController(ActionBootKeyboard.START_RECORDING_KEYS_SETTINGS)
    private val stopRecordKeysSettings = KeyboardButtonActionController(ActionBootKeyboard.STOP_RECORDING_KEYS_SETTINGS)
    private val languageComboBox = ChooseLanguageActionBootComboBox().createController().invoke()

    init {
        initChangeDetectors()
        loadGlobalSettings()
    }

    override fun backToPreviousWindow() {
        SliderAnimation.backToTheWindow(mainController.tabPane, this, mainController.centerStackPane)
    }

    override fun saveSettings() {
        ApplicationLauncher.globalSettings.run {
            stopActionKeys = ArrayList(stopKeysSetting.keyboardField.keys)
            startRecordKeys = ArrayList(startRecordKeysSettings.keyboardField.keys)
            stopRecordKeys = ArrayList(stopRecordKeysSettings.keyboardField.keys)
            language = languageComboBox.comboBox.value
        }
        GlobalSettingsLoader.save()
        saveButton.isDisable = true
        setMessageToSnackBar(fromBundle("message.snackbar.settingsSave"))
    }

    override fun initChangeDetectors() {
        stopKeysSetting.keyboardField.keys.addListener(ListChangeListener { changeDetect() })
        startRecordKeysSettings.keyboardField.keys.addListener(ListChangeListener { changeDetect() })
        stopRecordKeysSettings.keyboardField.keys.addListener(ListChangeListener { changeDetect() })
        languageComboBox.comboBox.valueProperty().addListener { _ -> changeDetect() }
    }

    private fun loadGlobalSettings() {
        mainVBox.children.addAll(
                stopKeysSetting,
                languageComboBox,
                startRecordKeysSettings,
                stopRecordKeysSettings)
        reloadSettingsValue()
    }

    fun reloadSettingsValue() {
        ApplicationLauncher.globalSettings.run {
            stopKeysSetting.keyboardField.setKeyboardButtons(stopActionKeys)
            startRecordKeysSettings.keyboardField.setKeyboardButtons(startRecordKeys)
            stopRecordKeysSettings.keyboardField.setKeyboardButtons(stopRecordKeys)
            languageComboBox.comboBox.value = language
        }
        saveButton.isDisable = true
    }

}
