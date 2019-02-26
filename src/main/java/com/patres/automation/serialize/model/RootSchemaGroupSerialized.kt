package com.patres.automation.serialize.model

import kotlinx.serialization.*

@Serializable
data class RootSchemaGroupSerialized(
        val schemaGroupSerialized: SchemaGroupSerialized
)