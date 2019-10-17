package com.patres.automation.settings

import com.patres.automation.Main
import com.patres.automation.mapper.AutomationMapper
import java.io.File

object GlobalSettingsLoader {

    private const val path = "Settings.txt"

    fun load(): GlobalSettings {
        try {
            val file = File(path)
            if (file.exists()) {
                val serializedGlobalSettings = file.readText()
                return AutomationMapper.toObject(serializedGlobalSettings)
            }
            return GlobalSettings()
        } catch (e: Exception) {
            return GlobalSettings()
        }
    }

    fun save(globalSettings: GlobalSettings = Main.globalSettings) {
        val filesToSave = Main.mainController?.tabContainers?.map { it.rootSchema.getFilePathToSettings() }
        filesToSave?.let { files ->
            globalSettings.previousPathFiles = files
        }

        val serializedRootGroup = AutomationMapper.toJson(globalSettings)
        val file = File(path)
        file.writeText(serializedRootGroup)
    }

}