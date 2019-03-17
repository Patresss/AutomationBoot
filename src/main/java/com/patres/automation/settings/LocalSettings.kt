package com.patres.automation.settings

import com.patres.automation.Main
import com.patres.automation.keyboard.KeyboardKey
import kotlinx.serialization.Serializable

@Serializable
data class LocalSettings(
        var stopKeys: List<KeyboardKey> = emptyList()
) {

    fun loadStopKeys(): List<KeyboardKey> {
        if (stopKeys.isEmpty()) {
            return Main.globalSettings.stopKeys
        }
        return stopKeys
    }

}