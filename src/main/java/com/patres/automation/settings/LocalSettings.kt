package com.patres.automation.settings

import com.patres.automation.ApplicationLauncher

import com.patres.automation.keyboard.KeyboardKey

data class LocalSettings(
        var stopKeys: List<KeyboardKey> = emptyList(),
        var runKeysSetting: List<KeyboardKey> = emptyList(),
        var enableRest: Boolean = false,
        var endpointName: String = ""
) {

    fun loadStopKeys(): List<KeyboardKey> {
        if (stopKeys.isEmpty()) {
            return ApplicationLauncher.globalSettings.stopKeys
        }
        return stopKeys
    }

}