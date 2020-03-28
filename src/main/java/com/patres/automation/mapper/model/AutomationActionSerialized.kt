package com.patres.automation.mapper.model

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonSubTypes.Type
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.patres.automation.action.AbstractAction
import com.patres.automation.action.delay.DelayType
import com.patres.automation.action.mouse.MousePointAction
import com.patres.automation.gui.controller.box.AbstractBox
import com.patres.automation.gui.controller.box.ActionBox
import com.patres.automation.gui.controller.model.AutomationController
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
        Type(value = DelayActionSerialized::class, name = "DelayAction")
)
sealed class AutomationActionSerialized {
    abstract fun serializedToController(): AbstractBox<*>
    abstract fun serializedToModel(automationRunningProperty: BooleanProperty?): AbstractAction
    abstract fun toTranslatedString(): String
}

class MousePointActionSerialized(
        val actionType: ActionBootMousePoint,
        val point: String? = null,
        val image: String? = null,
        val threshold: Double? = null
) : AutomationActionSerialized() {
    override fun serializedToController() = ActionBox(MousePointActionMapper.serializedToController(this))
    override fun serializedToModel(automationRunningProperty: BooleanProperty?) = MousePointActionMapper.serializedToModel(this, automationRunningProperty)
    override fun toTranslatedString() = "• ${LanguageManager.getLanguageString(actionType.bundleName)}: $point"
}

class KeyboardFieldActionSerialized(
        val actionType: ActionBootKeyboard,
        val keys: List<KeyboardKey> = emptyList()
) : AutomationActionSerialized() {
    override fun serializedToController() = ActionBox(KeyboardFieldActionMapper.serializedToController(this))
    override fun serializedToModel(automationRunningProperty: BooleanProperty?) = KeyboardFieldActionMapper.serializedToModel(this, automationRunningProperty)
    override fun toTranslatedString() = "• ${LanguageManager.getLanguageString(actionType.bundleName)}: $keys"
}

class SchemaGroupSerialized(
        val actionList: List<AutomationActionSerialized> = ArrayList(),
        val groupName: String = "Group",
        val numberOfIterations: String = "1"
)  : AutomationActionSerialized() {
    override fun serializedToController() = SchemaGroupMapper.serializedToController(this)
    override fun serializedToModel(automationRunningProperty: BooleanProperty?) = SchemaGroupMapper.serializedToModel(this, automationRunningProperty)
    override fun toTranslatedString() = "• ${LanguageManager.getLanguageString("group")}: $groupName"

}

class BrowserActionSerialized(
        val actionType: ActionBootBrowser,
        val path: String = ""
) : AutomationActionSerialized() {
    override fun serializedToController() = ActionBox(BrowserActionMapper.serializedToController(this))
    override fun serializedToModel(automationRunningProperty: BooleanProperty?) = BrowserActionMapper.serializedToModel(this, automationRunningProperty)
    override fun toTranslatedString() = "• ${LanguageManager.getLanguageString(actionType.bundleName)}: $path"
}

class TextFieldActionSerialized(
        val actionType: ActionBootTextField,
        val value: String = ""
) : AutomationActionSerialized() {
    override fun serializedToController() = ActionBox(TextFieldActionMapper.serializedToController(this))
    override fun serializedToModel(automationRunningProperty: BooleanProperty?) = TextFieldActionMapper.serializedToModel(this, automationRunningProperty)
    override fun toTranslatedString() = "• ${LanguageManager.getLanguageString(actionType.bundleName)}: $value"

}

class TextAreaActionSerialized(
        val actionType: ActionBootTextArea,
        val value: String = ""
) : AutomationActionSerialized() {
    override fun serializedToController() = ActionBox(TextAreaActionMapper.serializedToController(this))
    override fun serializedToModel(automationRunningProperty: BooleanProperty?) = TextAreaActionMapper.serializedToModel(this, automationRunningProperty)
    override fun toTranslatedString() = "• ${LanguageManager.getLanguageString(actionType.bundleName)}: $value"
}

class DelayActionSerialized(
        val actionType: ActionBootDelay,
        val value: String = "",
        val delayType: DelayType = DelayType.MILLISECONDS
) : AutomationActionSerialized() {
    override fun serializedToController() = ActionBox(DelayActionMapper.serializedToController(this))
    override fun serializedToModel(automationRunningProperty: BooleanProperty?) = DelayActionMapper.serializedToModel(this, automationRunningProperty)
    override fun toTranslatedString() = "• ${LanguageManager.getLanguageString(actionType.bundleName)}: $value, $delayType"

}