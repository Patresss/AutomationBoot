package com.patres.automation.serialize.model

import kotlinx.serialization.Serializable


@Serializable
open class TextActionSerialized(
        val actionNodeValue: String = "",
        val actionName: String
) : AutomationActionSerialized()