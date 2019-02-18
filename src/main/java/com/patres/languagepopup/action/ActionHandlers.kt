package com.patres.languagepopup.action

import com.patres.languagepopup.model.RootSchemaGroupModel

object ActionHandlers {


    val moveToDown: (rootSchemaGroupModel: RootSchemaGroupModel) -> Unit = { it.selectedModel?.downActionBlock() }

    val moveToUp: (rootSchemaGroupModel: RootSchemaGroupModel) -> Unit = { it.selectedModel?.upActionBlock() }

    val remove: (rootSchemaGroupModel: RootSchemaGroupModel) -> Unit = { it.removeSelectedModel() }

    val addSchemeGroup: (rootSchemaGroupModel: RootSchemaGroupModel) -> Unit = { it.addSchemaGroup() }

    val addLeftClickMouse: (rootSchemaGroupModel: RootSchemaGroupModel) -> Unit = { it.addTextAction(Action.CLICK_LEFT_MOUSE_BUTTON) }

}