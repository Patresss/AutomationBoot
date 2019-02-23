package com.patres.automation.menuItem

import com.patres.automation.model.RootSchemaGroupModel

object MenuItemValidators {

    val isNotSelectedAction: (rootSchemaGroupModel: RootSchemaGroupModel) -> Boolean = { it.selectedModel == null }

    val isNotSelectedActionOrIsRoot: (rootSchemaGroupModel: RootSchemaGroupModel) -> Boolean = { it.selectedModel == null || it.schemaGroup == it.selectedModel }

}