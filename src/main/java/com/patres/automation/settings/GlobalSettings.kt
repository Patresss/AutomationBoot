package com.patres.automation.settings

import com.patres.automation.keyboard.KeyboardKey
import kotlin.properties.Delegates

class GlobalSettings(
        var stopKeys: List<KeyboardKey> = listOf(KeyboardKey.ESCAPE),
        var previousPathFiles: List<String> = emptyList(),
        var lastLoadedPath: String? = null,
        language: Language = Language.ENGLISH
) {
    var language: Language by Delegates.observable(language) { _, _, newValue ->
        LanguageManager.setLanguage(newValue)
    }
}