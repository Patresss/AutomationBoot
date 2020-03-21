package com.patres.automation.gui.controller.saveBackScreen.settings

import com.patres.automation.ApplicationLauncher
import com.patres.automation.gui.animation.SliderAnimation
import com.patres.automation.gui.component.snackBar.SnackBarType
import com.patres.automation.gui.component.snackBar.addMessageLanguageWhenIsLoaded
import com.patres.automation.gui.controller.model.AutomationController
import com.patres.automation.gui.controller.model.KeyboardButtonActionController
import com.patres.automation.gui.controller.model.RootSchemaGroupController
import com.patres.automation.gui.controller.model.TextFieldActionController
import com.patres.automation.gui.controller.saveBackScreen.SaveBackScreenController
import com.patres.automation.settings.LocalSettings
import com.patres.automation.type.ActionBootKeyboard
import com.patres.automation.type.ActionBootTextField
import javafx.collections.ListChangeListener


class LocalSettingsController(
        private val rootSchemaGroupController: RootSchemaGroupController,
        private val settings: LocalSettings
) : SaveBackScreenController("menu.settings.localSettings") {

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

    override fun saveChanges() {
        allSettings.forEach { it.checkValidation() }
        settings.run {
            runActionsKeys = runKeysSetting.keyboardField.keys
            stopActionsKeys = stopKeysSetting.keyboardField.keys
            endpointName = endpointNameTextField.value
        }
        snackBar.addMessageLanguageWhenIsLoaded(isLoaded, SnackBarType.INFO, "message.snackbar.settingsSave")
    }

    override fun initChangeDetectors() {
        runKeysSetting.keyboardField.keys.addListener(ListChangeListener { changeDetect() })
        stopKeysSetting.keyboardField.keys.addListener(ListChangeListener { changeDetect() })
        endpointNameTextField.valueText.textProperty().addListener { _, _, _ -> changeDetect() }
    }

    override fun backToPreviousWindow() {
        SliderAnimation.backToTheWindow(rootSchemaGroupController.rootBorderPane, this, rootSchemaGroupController)
    }

    private fun loadLocalSettings() {
        mainVBox.children.addAll(allSettings)
    }

    override fun reloadSettings() {
        settings.run {
            runKeysSetting.keyboardField.setKeyboardButtons(runActionsKeys)
            stopKeysSetting.keyboardField.setKeyboardButtons(stopActionsKeys)
            endpointNameTextField.value = endpointName
            endpointNameTextField.valueText.promptText = rootSchemaGroupController.model.getEndpointName()
        }
    }

}
