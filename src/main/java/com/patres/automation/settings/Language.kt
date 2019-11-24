package com.patres.automation.settings

import java.util.*

enum class Language(val local: Locale, private val languageName: String) {

    ENGLISH(Locale("en"), "english"),
    POLISH(Locale("pl"), "polish");


    override fun toString(): String {
        return LanguageManager.getLanguageString("settings.chooseLanguage.$languageName")
    }
}