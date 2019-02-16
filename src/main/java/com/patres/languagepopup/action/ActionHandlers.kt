package com.patres.languagepopup.action

import com.patres.languagepopup.model.RootSchemaGroupModel

object ActionHandlers {

    val moveToDown: (rootSchemaGroupModel: RootSchemaGroupModel) -> Unit = { it.selectedModel?.downActionBlock() }

    val moveToUp: (rootSchemaGroupModel: RootSchemaGroupModel) -> Unit = { it.selectedModel?.upActionBlock() }

    val remove: (rootSchemaGroupModel: RootSchemaGroupModel) -> Unit = { it.removeSelectedModel() }

    val addLeftClickMouse: (rootSchemaGroupModel: RootSchemaGroupModel) -> Unit = { it.addTextAction() }

}