package com.patres.languagepopup.action

import com.patres.languagepopup.model.RootSchemaGroupModel

object ActionValidators {

    val isNotSelectedAction: (rootSchemaGroupModel: RootSchemaGroupModel) -> Boolean = { it.selectedModel == null }

}