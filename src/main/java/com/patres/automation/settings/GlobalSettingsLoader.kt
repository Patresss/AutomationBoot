package com.patres.automation.settings

import kotlinx.serialization.json.Json
import java.io.File

object GlobalSettingsLoader {

    private const val path = "Settings.txt"

    fun load(): GlobalSettings {
        try {
            val file = File(path)
            if (file.exists()) {
                val serializedGlobalSettings = file.readText()
                return Json.parse(GlobalSettings.serializer(), serializedGlobalSettings)
            }
            return GlobalSettings()
        } catch (e: Exception) {
            return GlobalSettings()
        }
    }

    fun save(globalSettings: GlobalSettings) {
        val serializedRootGroup = Json.stringify(GlobalSettings.serializer(), globalSettings)
        val file = File(path)
        file.writeText(serializedRootGroup)
    }

}