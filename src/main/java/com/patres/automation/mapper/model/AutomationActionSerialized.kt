package com.patres.automation.mapper.model

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonSubTypes.Type
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.patres.automation.action.AbstractAction
import com.patres.automation.action.delay.TimeContainer
import com.patres.automation.gui.controller.box.AbstractBox
import com.patres.automation.gui.controller.box.ActionBox
import com.patres.automation.keyboard.KeyboardKey
import com.patres.automation.mapper.*
import com.patres.automation.settings.LanguageManager
import com.patres.automation.type.*
import javafx.beans.property.BooleanProperty

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
        Type(value = TextAreaActionSerialized::class, name = "TextAreaAction"),
        Type(value = TimeActionSerialized::class, name = "DelayAction")
)
sealed class AutomationActionSerialized {
    abstract fun serializedToController(): AbstractBox<*>
    abstract fun serializedToModel(automationRunningProperty: BooleanProperty): AbstractAction
    abstract fun toTranslatedString(): String
}

class MousePointActionSerialized(
        val actionBootType: ActionBootMousePoint,
        val point: String? = null,
        val image: String? = null,
        val threshold: Double? = null,
        val ignoreIfNotFound: Boolean = true
) : AutomationActionSerialized() {
    override fun serializedToController() = ActionBox(MousePointActionMapper.serializedToController(this))
    override fun serializedToModel(automationRunningProperty: BooleanProperty) = MousePointActionMapper.serializedToModel(this, automationRunningProperty)
    override fun toTranslatedString() = "• ${LanguageManager.getLanguageString(actionBootType.bundleName)}: $point"
}

class KeyboardFieldActionSerialized(
        val actionBootType: ActionBootKeyboard,
        val keys: List<KeyboardKey> = emptyList()
) : AutomationActionSerialized() {
    override fun serializedToController() = ActionBox(KeyboardFieldActionMapper.serializedToController(this))
    override fun serializedToModel(automationRunningProperty: BooleanProperty) = KeyboardFieldActionMapper.serializedToModel(this, automationRunningProperty)
    override fun toTranslatedString() = "• ${LanguageManager.getLanguageString(actionBootType.bundleName)}: $keys"
}

class SchemaGroupSerialized(
        val actionList: List<AutomationActionSerialized> = ArrayList(),
        val groupName: String = "Group",
        val numberOfIterations: String = "1"
) : AutomationActionSerialized() {
    override fun serializedToController() = SchemaGroupMapper.serializedToController(this)
    override fun serializedToModel(automationRunningProperty: BooleanProperty) = SchemaGroupMapper.serializedToModel(this, automationRunningProperty)
    override fun toTranslatedString() = "• ${LanguageManager.getLanguageString("group")}: $groupName"

}

class BrowserActionSerialized(
        val actionBootType: ActionBootBrowser,
        val path: String = ""
) : AutomationActionSerialized() {
    override fun serializedToController() = ActionBox(BrowserActionMapper.serializedToController(this))
    override fun serializedToModel(automationRunningProperty: BooleanProperty) = BrowserActionMapper.serializedToModel(this, automationRunningProperty)
    override fun toTranslatedString() = "• ${LanguageManager.getLanguageString(actionBootType.bundleName)}: $path"
}

class TextFieldActionSerialized(
        val actionBootType: ActionBootTextField,
        val value: String = ""
) : AutomationActionSerialized() {
    override fun serializedToController() = ActionBox(TextFieldActionMapper.serializedToController(this))
    override fun serializedToModel(automationRunningProperty: BooleanProperty) = TextFieldActionMapper.serializedToModel(this, automationRunningProperty)
    override fun toTranslatedString() = "• ${LanguageManager.getLanguageString(actionBootType.bundleName)}: $value"

}

class TextAreaActionSerialized(
        val actionBootType: ActionBootTextArea,
        val value: String = ""
) : AutomationActionSerialized() {
    override fun serializedToController() = ActionBox(TextAreaActionMapper.serializedToController(this))
    override fun serializedToModel(automationRunningProperty: BooleanProperty) = TextAreaActionMapper.serializedToModel(this, automationRunningProperty)
    override fun toTranslatedString() = "• ${LanguageManager.getLanguageString(actionBootType.bundleName)}: $value"
}

class TimeActionSerialized(
        val actionBootType: ActionBootTime,
        val timeContainer: TimeContainer = TimeContainer(0)
) : AutomationActionSerialized() {
    override fun serializedToController() = ActionBox(TimeActionMapper.serializedToController(this))
    override fun serializedToModel(automationRunningProperty: BooleanProperty) = TimeActionMapper.serializedToModel(this, automationRunningProperty)
    override fun toTranslatedString() = "• ${LanguageManager.getLanguageString(actionBootType.bundleName)}: $timeContainer"

}