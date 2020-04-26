package com.patres.automation.mapper.model

import com.patres.automation.settings.LocalSettings
import com.patres.automation.system.ApplicationInfo

data class RootSchemaGroupSerialized(
        val schemaGroupSerialized: SchemaGroupSerialized,
        val localSettings: LocalSettings,
        val orgFile: String?,
        var applicationVersion: String? = ApplicationInfo.version
)