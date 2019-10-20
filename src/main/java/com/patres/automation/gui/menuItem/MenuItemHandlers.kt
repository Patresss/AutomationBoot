package com.patres.automation.gui.menuItem

import com.patres.automation.action.RootSchemaGroupModel

object MenuItemHandlers {

    val runAutomation: (rootSchemaGroupModel: RootSchemaGroupModel) -> Unit = { it.runAutomation() }
    val stopAutomation: (rootSchemaGroupModel: RootSchemaGroupModel) -> Unit = { it.stopAutomation() }
    val moveToDown: (rootSchemaGroupModel: RootSchemaGroupModel) -> Unit = { it.controller.selectedModel.downActionBlock() }
    val moveToUp: (rootSchemaGroupModel: RootSchemaGroupModel) -> Unit = { it.controller.selectedModel.upActionBlock() }
    val remove: (rootSchemaGroupModel: RootSchemaGroupModel) -> Unit = { it.controller.removeSelectedModel() }

}