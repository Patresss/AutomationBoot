package com.patres.automation.settings

import com.patres.automation.keyboard.KeyboardKey
import kotlinx.serialization.Serializable

@Serializable
data class GlobalSettings(
        var stopKeys: List<KeyboardKey> = emptyList()
)