package com.patres.languagepopup.action

import com.patres.languagepopup.model.RootSchemaGroupModel

object ActionValidators {

    val isNotSelectedAction: (rootSchemaGroupModel: RootSchemaGroupModel) -> Boolean = { it.selectedModel == null }

    val isNotSelectedActionOrIsRoot: (rootSchemaGroupModel: RootSchemaGroupModel) -> Boolean = { it.selectedModel == null || it.schemaGroup == it.selectedModel }

}