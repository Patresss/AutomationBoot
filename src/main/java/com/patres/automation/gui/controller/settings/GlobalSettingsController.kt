package com.patres.automation.gui.controller.settings

import com.patres.automation.Main
import com.patres.automation.gui.controller.MainController
import com.patres.automation.util.fromBundle
import javafx.animation.Interpolator
import javafx.animation.KeyFrame
import javafx.animation.KeyValue
import javafx.animation.Timeline
import javafx.util.Duration


class GlobalSettingsController(private val mainController: MainController) : SettingsController(fromBundle("menu.settings.globalSettings")) {

//    private val stopKeysSetting = KeyboardButtonActionController(labelText = fromBundle("settings.stopKeys"))

    init {
        initChangeDetectors()
        loadGlobalSettings()
    }

    override fun backToPreviousWindow() {
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


    override fun saveSettings() {
//        Main.globalSettings.stopKeys = ArrayList(stopKeysSetting.keyboardField.keys)
//        GlobalSettingsLoader.save(Main.globalSettings)
//        saveButton.isDisable = true
//        setMessageToSnackBar(fromBundle("message.snackbar.settingsSave"))
    }

    override fun initChangeDetectors() {
//        stopKeysSetting.keyboardField.keys.addListener(ListChangeListener { changeDetect() })
    }

    fun loadGlobalSettings() {
//        mainVBox.children.add(stopKeysSetting)
        reloadSettingsValue()
    }

    fun reloadSettingsValue() {
//        stopKeysSetting.keyboardField.setKeyboardButtons(Main.globalSettings.stopKeys)
        saveButton.isDisable = true
    }

}
