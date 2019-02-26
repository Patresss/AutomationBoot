package com.patres.automation.serialize.model

import kotlinx.serialization.Serializable


@Serializable
data class DelayActionSerialized(val delayActionValue: String) : ActionNodeSerialized(delayActionValue) {
}