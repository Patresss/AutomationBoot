package com.patres.automation.serialize.model

import kotlinx.serialization.Serializable


@Serializable
class SchemaGroupSerialized(
        val actionList: List<AutomationActionSerialized> = ArrayList(),
        val groupName: String = "Group",
        val numberOfIterations: String = "1"
) : AutomationActionSerialized()