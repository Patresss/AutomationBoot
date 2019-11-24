package com.patres.automation.settings

import javafx.beans.binding.Bindings
import javafx.beans.binding.StringBinding
import javafx.beans.property.ObjectProperty
import javafx.beans.property.SimpleObjectProperty
import org.slf4j.LoggerFactory
import java.text.MessageFormat
import java.util.*
import java.util.Locale
import java.util.concurrent.Callable


object LanguageManager {

    val logger = LoggerFactory.getLogger(LanguageManager::class.java)!!

    var locale: ObjectProperty<Locale> = SimpleObjectProperty(Language.ENGLISH.local).apply {
        addListener { _, _, newValue -> Locale.setDefault(newValue) }
    }

    fun setLocale(newLocale: Locale) {
        locale.set(newLocale)
        Locale.setDefault(newLocale)
    }

    fun createStringBinding(key: String, vararg args: Any): StringBinding {
        logger.debug("Get string property for key: $key")
        return Bindings.createStringBinding(Callable { getLanguageString(key, args) }, locale)
    }

    fun getLanguageString(key: String, vararg args: Any): String = MessageFormat.format(getBundle().getString(key), *args)

    fun getBundle(): ResourceBundle = ResourceBundle.getBundle("language/Bundle", locale.get())
}





