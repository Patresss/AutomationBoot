package com.patres.automation.settings

import com.patres.automation.keyboard.KeyboardKey
import kotlin.properties.Delegates

class GlobalSettings(
        var stopActionKeys: List<KeyboardKey> = listOf(KeyboardKey.ESCAPE),
        var startRecordKeys: List<KeyboardKey> = listOf(KeyboardKey.F11),
        var stopRecordKeys: List<KeyboardKey> = listOf(KeyboardKey.F12),
        var previousPathFiles: List<String> = emptyList(),
        var lastLoadedPath: String? = null,
        language: Language = Language.ENGLISH,
        var port: Int = 8012,
        var removeDelay: Boolean = true,
        var removeLastAction: Boolean = true,
        var enableRest: Boolean = true
        ) {
    var language: Language by Delegates.observable(language) { _, _, newValue ->
        LanguageManager.setLanguage(newValue)
    }
}