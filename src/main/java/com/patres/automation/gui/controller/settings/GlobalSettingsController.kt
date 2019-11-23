package com.patres.automation.gui.controller.settings

import com.patres.automation.Main
import com.patres.automation.gui.animation.SliderAnimation
import com.patres.automation.gui.controller.MainController
import com.patres.automation.gui.controller.model.ComboBoxController
import com.patres.automation.gui.controller.model.KeyboardButtonActionController
import com.patres.automation.settings.GlobalSettingsLoader
import com.patres.automation.type.ActionBootComboBox
import com.patres.automation.type.ActionBootKeyboard
import com.patres.automation.util.fromBundle
import javafx.collections.ListChangeListener


class GlobalSettingsController(private val mainController: MainController) : SettingsController("menu.settings.globalSettings") {

    private val stopKeysSetting = KeyboardButtonActionController(ActionBootKeyboard.STOP_KEYS_SETTINGS)
    private val language = ComboBoxController(ActionBootComboBox.CHOOSE_LANGUAGE)

    init {
        initChangeDetectors()
        loadGlobalSettings()
    }

    override fun backToPreviousWindow() {
        SliderAnimation.backToTheWindow(mainController.tabPane, this, mainController.centerStackPane)
    }

    override fun saveSettings() {
        Main.globalSettings.stopKeys = ArrayList(stopKeysSetting.keyboardField.keys)
        GlobalSettingsLoader.save(Main.globalSettings)
        saveButton.isDisable = true
        setMessageToSnackBar(fromBundle("message.snackbar.settingsSave"))
    }

    override fun initChangeDetectors() {
        stopKeysSetting.keyboardField.keys.addListener(ListChangeListener { changeDetect() })
//        language.comboBox.addListener(ListChangeListener { changeDetect() })
    }

    private fun loadGlobalSettings() {
        mainVBox.children.addAll(stopKeysSetting, language)
        reloadSettingsValue()
    }

    fun reloadSettingsValue() {
        stopKeysSetting.keyboardField.setKeyboardButtons(Main.globalSettings.stopKeys)
        saveButton.isDisable = true
    }

}
