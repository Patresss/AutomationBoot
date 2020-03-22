package com.patres.automation.settings

import com.patres.automation.keyboard.KeyboardKey
import com.patres.automation.server.ServerBoot
import org.slf4j.LoggerFactory
import java.io.File
import kotlin.properties.Delegates

class GlobalSettings(
        var stopActionKeys: List<KeyboardKey> = listOf(KeyboardKey.ESCAPE),
        var startRecordKeys: List<KeyboardKey> = listOf(KeyboardKey.F11),
        var stopRecordKeys: List<KeyboardKey> = listOf(KeyboardKey.F12),
        var previousPathFiles: List<String> = emptyList(),
        var lastLoadedPath: String? = null,
        language: Language = Language.ENGLISH,
        var port: Int = ServerBoot.DEFAULT_PORT,
        var removeDelay: Boolean = true,
        var removeLastAction: Boolean = true,
        var enableRest: Boolean = true,
        var activeSchemas: MutableList<String> = mutableListOf()
) {

    companion object {
        private val logger = LoggerFactory.getLogger(GlobalSettings::class.java)
    }

    var language: Language by Delegates.observable(language) { _, _, newValue ->
        LanguageManager.setLanguage(newValue)
    }

    fun calculatePreviousOpenModels(): Set<File> {
        val (existingFile, notExistingFiles) = previousPathFiles
                .map { File(it) }
                .partition { it.exists() }
        if (notExistingFiles.isNotEmpty()) {
            logger.warn("Cannot find files to open: $notExistingFiles")
        }
        return existingFile.toSet()
    }

    fun calculateActiveSchemasAsFiles(): List<File> {
        val (existingFile, notExistingFiles) = activeSchemas
                .map { File(it) }
                .partition { it.exists() }
        if (notExistingFiles.isNotEmpty()) {
            logger.warn("Cannot find files to active: $notExistingFiles")
        }
        return existingFile
    }

    fun editAndSave(block: GlobalSettings.() -> Unit) {
        this.block()
        GlobalSettingsLoader.save(this)
    }
}