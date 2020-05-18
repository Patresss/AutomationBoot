package com.patres.automation.system

import com.patres.automation.settings.LanguageManager
import com.patres.automation.util.UnicodeIcon.BULLET
import java.util.*

object ApplicationInfo {

    private const val UNKNOWN_VERSION: String = "UNKNOWN_VERSION"

    private val inputStream = ApplicationInfo::class.java.getResourceAsStream("/system/application.properties")
    private val properties = Properties().apply { load(inputStream) }

    val version: String = properties.getProperty("application.version")?: UNKNOWN_VERSION
    private val title = properties.getProperty("application.title")?: "Application Boot"
    private val description = properties.getProperty("application.description")?: ""
    private val author = properties.getProperty("application.author")?: ""
    private val contact = properties.getProperty("application.contact")?: ""
    private val website = properties.getProperty("application.website")?: ""


    fun getApplicationInfoDescription() = """
        $title
        $BULLET ${LanguageManager.getLanguageString("application.version")}: $version
        $BULLET ${LanguageManager.getLanguageString("application.author")}: $author
        $BULLET ${LanguageManager.getLanguageString("application.contact")}: $contact
        $BULLET ${LanguageManager.getLanguageString("application.website")}: $website
    """.trimIndent()

}