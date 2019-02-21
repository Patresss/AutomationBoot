package com.patres.languagepopup.menuItem

import com.patres.languagepopup.model.RootSchemaGroupModel

object MenuItemHandlers {


    val runAutomation: (rootSchemaGroupModel: RootSchemaGroupModel) -> Unit = { it.runAutomation() }

    val moveToDown: (rootSchemaGroupModel: RootSchemaGroupModel) -> Unit = { it.selectedModel?.downActionBlock() }

    val moveToUp: (rootSchemaGroupModel: RootSchemaGroupModel) -> Unit = { it.selectedModel?.upActionBlock() }

    val remove: (rootSchemaGroupModel: RootSchemaGroupModel) -> Unit = { it.removeSelectedModel() }

    val addSchemeGroup: (rootSchemaGroupModel: RootSchemaGroupModel) -> Unit = { it.addSchemaGroup() }

    val addLeftClickMouse: (rootSchemaGroupModel: RootSchemaGroupModel) -> Unit = { it.addTextAction(MenuItem.CLICK_LEFT_MOUSE_BUTTON) }

}