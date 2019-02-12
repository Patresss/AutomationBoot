package com.patres.languagepopup.action

import com.patres.languagepopup.model.RootSchemaGroupModel

object ActionHandlers {

    val moveToDown: (rootSchemaGroupModel: RootSchemaGroupModel) -> Unit = { it.selectedModel?.controller?.downActionBlock() }

    val moveToUp: (rootSchemaGroupModel: RootSchemaGroupModel) -> Unit = { it.selectedModel?.controller?.upActionBlock() }

    val remove: (rootSchemaGroupModel: RootSchemaGroupModel) -> Unit = { it.removeSelectedModel() }

}