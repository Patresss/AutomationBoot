package com.patres.automation.gui.controller.settings

import com.patres.automation.ApplicationLauncher
import com.patres.automation.gui.animation.SliderAnimation
import com.patres.automation.gui.controller.MainController
import com.patres.automation.gui.controller.model.AutomationController
import com.patres.automation.gui.controller.model.CheckBoxActionController
import com.patres.automation.gui.controller.model.KeyboardButtonActionController
import com.patres.automation.gui.controller.model.TextFieldActionController
import com.patres.automation.gui.dialog.LogManager
import com.patres.automation.settings.GlobalSettingsLoader
import com.patres.automation.type.ActionBootCheckBox
import com.patres.automation.type.ActionBootKeyboard
import com.patres.automation.type.ActionBootTextField
import com.patres.automation.type.ChooseLanguageActionBootComboBox
import com.patres.automation.util.fromBundle
import javafx.collections.ListChangeListener


class GlobalSettingsController(private val mainController: MainController) : SettingsController("menu.settings.globalSettings") {

    private val stopKeysSetting = KeyboardButtonActionController(ActionBootKeyboard.STOP_KEYS_SETTINGS)
    private val startRecordKeysSettings = KeyboardButtonActionController(ActionBootKeyboard.START_RECORDING_KEYS_SETTINGS)
    private val stopRecordKeysSettings = KeyboardButtonActionController(ActionBootKeyboard.STOP_RECORDING_KEYS_SETTINGS)
    private val languageComboBox = ChooseLanguageActionBootComboBox().createController().invoke()

    private val enableRestCheckBox = CheckBoxActionController(ActionBootCheckBox.ENABLE_REST)
    private val portText = TextFieldActionController(ActionBootTextField.PORT)

    private val allSettings = listOf<AutomationController<*>>(
            languageComboBox,
            stopKeysSetting,
            startRecordKeysSettings,
            stopRecordKeysSettings,
            enableRestCheckBox,
            portText)

    init {
        initChangeDetectors()
        loadGlobalSettings()
    }

    override fun backToPreviousWindow() {
        SliderAnimation.backToTheWindow(mainController.tabPane, this, mainController.centerStackPane)
    }

    override fun saveSettings() {
        try {
            allSettings.forEach { it.checkValidation() }
            ApplicationLauncher.globalSettings.run {
                stopActionKeys = ArrayList(stopKeysSetting.keyboardField.keys)
                startRecordKeys = ArrayList(startRecordKeysSettings.keyboardField.keys)
                stopRecordKeys = ArrayList(stopRecordKeysSettings.keyboardField.keys)
                language = languageComboBox.comboBox.value
                port = portText.value.toInt()
                enableRest = enableRestCheckBox.checkBox.isSelected
            }
            GlobalSettingsLoader.save()
            saveButton.isDisable = true
            setMessageToSnackBar(fromBundle("message.snackbar.settingsSave"))
        } catch (e: Exception) {
            LogManager.showAndLogException(e)
        }
    }

    override fun initChangeDetectors() {
        stopKeysSetting.keyboardField.keys.addListener(ListChangeListener { changeDetect() })
        startRecordKeysSettings.keyboardField.keys.addListener(ListChangeListener { changeDetect() })
        stopRecordKeysSettings.keyboardField.keys.addListener(ListChangeListener { changeDetect() })
        languageComboBox.comboBox.valueProperty().addListener { _ -> changeDetect() }
        portText.valueText.textProperty().addListener { _ -> changeDetect() }
        enableRestCheckBox.checkBox.selectedProperty().addListener { _, _, newValue ->
            changeDetect()
            portText.isVisible = newValue
        }
    }

    private fun loadGlobalSettings() {
        mainVBox.children.addAll(allSettings)
        saveButton.isDisable = true
    }

    fun reloadSettingsValue() {
        ApplicationLauncher.globalSettings.run {
            stopKeysSetting.keyboardField.setKeyboardButtons(stopActionKeys)
            startRecordKeysSettings.keyboardField.setKeyboardButtons(startRecordKeys)
            stopRecordKeysSettings.keyboardField.setKeyboardButtons(stopRecordKeys)
            languageComboBox.comboBox.value = language
            enableRestCheckBox.checkBox.isSelected = enableRest
            portText.isVisible = enableRest
            portText.valueText.text = port.toString()
        }
    }

}
