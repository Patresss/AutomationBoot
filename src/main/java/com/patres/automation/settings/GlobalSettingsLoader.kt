package com.patres.automation.settings

import com.patres.automation.ApplicationLauncher

import com.patres.automation.mapper.AutomationMapper
import org.slf4j.LoggerFactory
import java.io.File

object GlobalSettingsLoader {

    private const val path = "Settings.json"
    private val logger = LoggerFactory.getLogger(GlobalSettingsLoader::class.java)


    fun load(): GlobalSettings {
        logger.info("Global settings are loading...")
        try {
            val file = File(path)
            if (file.exists()) {
                val serializedGlobalSettings = file.readText()
                val settings = AutomationMapper.toObject<GlobalSettings>(serializedGlobalSettings)
                logger.info("Global settings are loaded")
                return settings
            }
            logger.info("Global settings not found - creating new")
            return GlobalSettings()
        } catch (e: Exception) {
            logger.error("Exception during load Global settings - creating new", e)
            return GlobalSettings()
        }
    }

    fun save(globalSettings: GlobalSettings = ApplicationLauncher.globalSettings) {
        logger.info("Global settings are saving...")
        val filesToSave = ApplicationLauncher.mainController.tabContainers.map { it.rootSchema.getFilePathToSettings() }
        globalSettings.previousPathFiles = filesToSave

        val serializedRootGroup = AutomationMapper.toJson(globalSettings)
        val file = File(path)
        file.writeText(serializedRootGroup)
        logger.info("Global settings are saved")

    }

}