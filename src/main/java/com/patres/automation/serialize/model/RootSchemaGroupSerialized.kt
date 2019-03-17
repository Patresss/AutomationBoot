package com.patres.automation.serialize.model

import com.patres.automation.settings.LocalSettings
import kotlinx.serialization.Serializable

@Serializable
data class RootSchemaGroupSerialized(
        val schemaGroupSerialized: SchemaGroupSerialized,
        val localSettings: LocalSettings
)