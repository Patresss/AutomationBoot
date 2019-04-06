package com.patres.automation.settings

import com.patres.automation.Main
import com.patres.automation.keyboard.KeyboardKey
import kotlinx.serialization.Optional
import kotlinx.serialization.Serializable

@Serializable
data class LocalSettings(
        var stopKeys: List<KeyboardKey> = emptyList(),
        var runKeysSetting: List<KeyboardKey> = emptyList(),
        @Optional
        var enableRest: Boolean = false,
        @Optional
        var endpointName: String = ""
) {

    fun loadStopKeys(): List<KeyboardKey> {
        if (stopKeys.isEmpty()) {
            return Main.globalSettings.stopKeys
        }
        return stopKeys
    }

}