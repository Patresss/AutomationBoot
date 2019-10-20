package com.patres.automation.mapper.model

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonSubTypes.Type
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.patres.automation.gui.controller.model.AutomationController
import com.patres.automation.keyboard.KeyboardKey
import com.patres.automation.mapper.*
import com.patres.automation.type.*

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes(
        Type(value = KeyboardFieldActionSerialized::class, name = "KeyboardFieldAction"),
        Type(value = MousePointActionSerialized::class, name = "MousePointAction"),
        Type(value = SchemaGroupSerialized::class, name = "SchemaGroup"),
        Type(value = BrowserActionSerialized::class, name = "BrowserAction"),
        Type(value = TextFieldActionSerialized::class, name = "TextFieldAction"),
        Type(value = TextAreaActionSerialized::class, name = "TextAreaAction")
)
sealed class AutomationActionSerialized {
    abstract fun serializedToController(): AutomationController<*>
}

data class MousePointActionSerialized(
        val actionType: ActionBootMousePoint,
        val point: String? = null,
        val image: String? = null,
        val threshold: Double? = null
) : AutomationActionSerialized() {
    override fun serializedToController() = MousePointActionMapper.serializedToController(this)
}

data class KeyboardFieldActionSerialized(
        val actionType: ActionBootKeyboard,
        val keys: List<KeyboardKey> = emptyList()
) : AutomationActionSerialized() {
    override fun serializedToController() = KeyboardFieldActionMapper.serializedToController(this)
}

data class SchemaGroupSerialized(
        val actionList: List<AutomationActionSerialized> = ArrayList(),
        val groupName: String = "Group",
        val numberOfIterations: String = "1"
) : AutomationActionSerialized() {
    override fun serializedToController() = SchemaGroupMapper.serializedToController(this)
}

data class BrowserActionSerialized(
        val actionType: ActionBootBrowser,
        val path: String = ""
) : AutomationActionSerialized() {
    override fun serializedToController() = BrowserActionMapper.serializedToController(this)
}

data class TextFieldActionSerialized(
        val actionType: ActionBootTextField,
        val value: String = ""
) : AutomationActionSerialized() {
    override fun serializedToController() = TextFieldActionMapper.serializedToController(this)
}

data class TextAreaActionSerialized(
        val actionType: ActionBootTextArea,
        val value: String = ""
) : AutomationActionSerialized() {
    override fun serializedToController() = TextAreaActionMapper.serializedToController(this)
}