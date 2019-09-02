package com.patres.automation.serialize.model

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonSubTypes.Type
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.patres.automation.keyboard.KeyboardKey

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes(
        Type(value = KeyboardFieldActionSerialized::class, name = "KeyboardFieldAction"),
        Type(value = MousePointActionSerialized::class, name = "MousePointAction"),
        Type(value = SchemaGroupSerialized::class, name = "SchemaGroup"),
        Type(value = TextActionSerialized::class, name = "TextAction")
)
sealed class AutomationActionSerialized

data class MousePointActionSerialized(
        val actionNodeValue: String = "",
        val image: String? = null,
        val threshold: Double? = null,
        val actionName: String
) : AutomationActionSerialized()

data class KeyboardFieldActionSerialized(
        val actionNodeValue: List<KeyboardKey>,
        val actionName: String
) : AutomationActionSerialized()


data class SchemaGroupSerialized(
        val actionList: List<AutomationActionSerialized> = ArrayList(),
        val groupName: String = "Group",
        val numberOfIterations: String = "1"
) : AutomationActionSerialized()

data class TextActionSerialized(
        val actionNodeValue: String = "",
        val actionName: String
) : AutomationActionSerialized()