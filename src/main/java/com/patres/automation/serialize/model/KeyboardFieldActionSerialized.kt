package com.patres.automation.serialize.model

import com.patres.automation.keyboard.KeyboardKey
import kotlinx.serialization.Serializable


@Serializable
open class KeyboardFieldActionSerialized(
        val actionNodeValue: List<KeyboardKey>,
        val actionName: String
) : AutomationActionSerialized()