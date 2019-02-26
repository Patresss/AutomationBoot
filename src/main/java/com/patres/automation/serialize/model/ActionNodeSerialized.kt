package com.patres.automation.serialize.model

import kotlinx.serialization.Serializable


@Serializable
open class ActionNodeSerialized (
        val actionValue: String = ""
): AutomationActionSerialized() {
}