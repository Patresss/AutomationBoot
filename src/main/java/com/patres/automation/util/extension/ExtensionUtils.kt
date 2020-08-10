package com.patres.automation.util.extension

import com.patres.automation.settings.LanguageManager
import org.slf4j.LoggerFactory
import kotlin.reflect.KClass

val logger = LoggerFactory.getLogger("ExtensionUtils.kt")!!

fun getObjectFromField(clazz: KClass<out Any>, instance: Any, nameOfField: String): Any {
    val field = clazz.java.getDeclaredField(nameOfField)
    field.isAccessible = true
    return field.get(instance)
}

fun fromBundle(key: String): String = LanguageManager.getLanguageString(key)

fun String?.toLongOrZero(): Long {
    return try {
        this?.toLong() ?: 0L
    } catch (e: Exception) {
        logger.error("Cannot parse String to Long", e)
        0L
    }
}