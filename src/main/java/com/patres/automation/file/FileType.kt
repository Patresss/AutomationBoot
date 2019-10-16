package com.patres.automation.file

enum class FileType(
        val type: String,
        val extension: String
) {

    BAT("Script", "bat"),
    AUTOMATION_BOOT("Automation Boot", "aJson")

}