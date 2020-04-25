package com.patres.automation.system

import java.util.*

object ApplicationInfo {

    private val inputStream = ApplicationInfo::class.java.getResourceAsStream("/system/application.properties")
    private val properties = Properties().apply { load(inputStream) }

    const val UNKNOWN_VERSION: String = "UNKNOWN_VERSION"

    val version: String = properties.getProperty("application.version")?: UNKNOWN_VERSION
    val title = properties.getProperty("application.title")?: "Application Boot"
    val description = properties.getProperty("application.description")?: ""

}