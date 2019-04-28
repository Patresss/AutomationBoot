package com.patres.automation.serialize.model

import kotlinx.serialization.Optional
import kotlinx.serialization.Serializable


@Serializable
open class MousePointActionSerialized(
        @Optional val actionNodeValue: String = "",
        @Optional val image: String? = null,
        @Optional val threshold: Double? = null,
        val actionName: String
) : AutomationActionSerialized()