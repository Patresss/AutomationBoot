package com.patres.automation.menuItem

import com.patres.automation.model.RootSchemaGroupModel

object MenuItemValidators {
    val isNotSelectedActionOrIsRoot: (rootSchemaGroupModel: RootSchemaGroupModel) -> Boolean = { it.schemaGroupController == it.selectedModel }
}