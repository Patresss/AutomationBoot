package com.patres.automation.gui.controller.settings

import com.patres.automation.ApplicationLauncher
import com.patres.automation.gui.animation.SliderAnimation
import com.patres.automation.gui.controller.model.AutomationController
import com.patres.automation.gui.controller.model.KeyboardButtonActionController
import com.patres.automation.gui.controller.model.RootSchemaGroupController
import com.patres.automation.gui.controller.model.TextFieldActionController
import com.patres.automation.gui.dialog.LogManager
import com.patres.automation.settings.LanguageManager
import com.patres.automation.settings.LocalSettings
import com.patres.automation.type.ActionBootKeyboard
import com.patres.automation.type.ActionBootTextField
import javafx.collections.ListChangeListener


class LocalSettingsController(
        private val rootSchemaGroupController: RootSchemaGroupController,
        private val settings: LocalSettings
) : SettingsController("menu.settings.localSettings") {

    private val runKeysSetting = KeyboardButtonActionController(ActionBootKeyboard.RUN_KEYS_SETTINGS)
    private val stopKeysSetting = KeyboardButtonActionController(ActionBootKeyboard.STOP_KEYS_SETTINGS)
    private val endpointNameTextField = TextFieldActionController(ActionBootTextField.ENDPOINT_NAME)

    private val allSettings = listOf<AutomationController<*>>(
            runKeysSetting,
            stopKeysSetting,
            endpointNameTextField)

    init {
        loadLocalSettings()
        initChangeDetectors()
        endpointNameTextField.isVisible = ApplicationLauncher.globalSettings.enableRest
    }

    override fun backToPreviousWindow() {
        SliderAnimation.backToTheWindow(rootSchemaGroupController.rootBorderPane, this, rootSchemaGroupController)
    }

    override fun saveSettings() {
        try {
            allSettings.forEach { it.checkValidation() }
            settings.run {
                runActionsKeys = runKeysSetting.keyboardField.keys
                stopActionsKeys = stopKeysSetting.keyboardField.keys
                endpointName = endpointNameTextField.value
            }
            saveButton.isDisable = true
            setMessageToSnackBar(LanguageManager.getLanguageString("message.snackbar.settingsSave"))
        } catch (e: Exception) {
            LogManager.showAndLogException(e)
        }
    }

    override fun initChangeDetectors() {
        runKeysSetting.keyboardField.keys.addListener(ListChangeListener { changeDetect() })
        stopKeysSetting.keyboardField.keys.addListener(ListChangeListener { changeDetect() })
        endpointNameTextField.valueText.textProperty().addListener { _, _, _ -> changeDetect() }
    }

    private fun loadLocalSettings() {
        mainVBox.children.addAll(allSettings)

        runKeysSetting.keyboardField.setKeyboardButtons(settings.runActionsKeys)
        stopKeysSetting.keyboardField.setKeyboardButtons(settings.stopActionsKeys)
        endpointNameTextField.value = settings.endpointName
        endpointNameTextField.valueText.promptText = rootSchemaGroupController.model.getEndpointName()
    }

}
