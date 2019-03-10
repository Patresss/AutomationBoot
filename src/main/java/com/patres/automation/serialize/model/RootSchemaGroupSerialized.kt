package com.patres.automation.serialize.model

import kotlinx.serialization.Serializable

@Serializable
data class RootSchemaGroupSerialized(
        val schemaGroupSerialized: SchemaGroupSerialized
)