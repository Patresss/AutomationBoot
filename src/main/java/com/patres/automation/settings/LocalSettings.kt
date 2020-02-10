package com.patres.automation.settings

import com.patres.automation.keyboard.KeyboardKey

data class LocalSettings(
        var stopActionsKeys: List<KeyboardKey> = emptyList(),
        var runActionsKeys: List<KeyboardKey> = emptyList(),
        var enableRest: Boolean = false,
        var endpointName: String = ""
)