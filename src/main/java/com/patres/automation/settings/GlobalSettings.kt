package com.patres.automation.settings

import com.patres.automation.keyboard.KeyboardKey

data class GlobalSettings(
        var stopKeys: List<KeyboardKey> = listOf(KeyboardKey.ESCAPE),
        var previousPathFiles: List<String> = emptyList(),
        var lastLoadedPath: String? = null
)