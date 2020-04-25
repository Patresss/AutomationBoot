package com.patres.automation.util.extension

import com.patres.automation.settings.LanguageManager
import kotlin.reflect.KClass

fun getObjectFromField(clazz: KClass<out Any>, instance: Any, nameOfField: String): Any {
    val field = clazz.java.getDeclaredField(nameOfField)
    field.isAccessible = true
    return field.get(instance)
}

fun fromBundle(key: String): String = LanguageManager.getLanguageString(key)
