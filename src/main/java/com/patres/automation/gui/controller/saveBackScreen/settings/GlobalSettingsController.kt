package com.patres.automation.gui.controller.saveBackScreen.settings

import com.patres.automation.ApplicationLauncher
import com.patres.automation.gui.animation.SliderAnimation
import com.patres.automation.gui.component.snackBar.SnackBarType
import com.patres.automation.gui.component.snackBar.addMessageLanguageWhenIsLoaded
import com.patres.automation.gui.controller.MainController
import com.patres.automation.gui.controller.model.AutomationController
import com.patres.automation.gui.controller.model.CheckBoxActionController
import com.patres.automation.gui.controller.model.KeyboardButtonActionController
import com.patres.automation.gui.controller.model.TextFieldActionController
import com.patres.automation.gui.controller.saveBackScreen.SaveBackScreenController
import com.patres.automation.type.ActionBootCheckBox
import com.patres.automation.type.ActionBootKeyboard
import com.patres.automation.type.ActionBootTextField
import com.patres.automation.type.ChooseLanguageActionBootComboBox
import javafx.collections.ListChangeListener


class GlobalSettingsController(private val mainController: MainController) : SaveBackScreenController("menu.settings.globalSettings") {

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
        loadGlobalSettings()
        initChangeDetectors()
    }

    override fun saveChanges() {
        allSettings.forEach { it.checkValidation() }
        ApplicationLauncher.globalSettings.editAndSave {
            stopActionKeys = ArrayList(stopKeysSetting.keyboardField.keys)
            startRecordKeys = ArrayList(startRecordKeysSettings.keyboardField.keys)
            stopRecordKeys = ArrayList(stopRecordKeysSettings.keyboardField.keys)
            language = languageComboBox.comboBox.value
            port = portText.value.toInt()
            enableRest = enableRestCheckBox.checkBox.isSelected
        }
        snackBar.addMessageLanguageWhenIsLoaded(isLoaded, SnackBarType.INFO, "message.snackbar.settingsSave")
    }

    override fun initChangeDetectors() {
        stopKeysSetting.keyboardField.keys.addListener(ListChangeListener { changeDetect() })
        startRecordKeysSettings.keyboardField.keys.addListener(ListChangeListener { changeDetect() })
        stopRecordKeysSettings.keyboardField.keys.addListener(ListChangeListener { changeDetect() })
        languageComboBox.comboBox.valueProperty().addListener { _ -> changeDetect() }
        portText.valueText.textProperty().addListener { _ ->
            snackBar.addMessageLanguageWhenIsLoaded(isLoaded, SnackBarType.WARNING, "message.snackbar.changesAppliedAfterRestart")
            changeDetect()
        }
        enableRestCheckBox.checkBox.selectedProperty().addListener { _, _, newValue ->
            snackBar.addMessageLanguageWhenIsLoaded(isLoaded, SnackBarType.WARNING, "message.snackbar.changesAppliedAfterRestart")
            portText.isVisible = newValue
            changeDetect()
        }
    }

    private fun loadGlobalSettings() {
        mainVBox.children.addAll(allSettings)
    }

    override fun backToPreviousWindow() {
        SliderAnimation.backToTheWindow(mainController.tabPane, this, mainController.centerStackPane)
    }

    override fun reloadSettings() {
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
