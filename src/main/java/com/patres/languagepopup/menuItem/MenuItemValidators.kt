package com.patres.languagepopup.menuItem

import com.patres.languagepopup.model.RootSchemaGroupModel

object MenuItemValidators {

    val isNotSelectedAction: (rootSchemaGroupModel: RootSchemaGroupModel) -> Boolean = { it.selectedModel == null }

    val isNotSelectedActionOrIsRoot: (rootSchemaGroupModel: RootSchemaGroupModel) -> Boolean = { it.selectedModel == null || it.schemaGroup == it.selectedModel }

}