package com.patres.automation.menuItem

import com.patres.automation.model.RootSchemaGroupModel

object MenuItemHandlers {

    val runAutomation: (rootSchemaGroupModel: RootSchemaGroupModel) -> Unit = { it.runAutomation() }
    val stopAutomation: (rootSchemaGroupModel: RootSchemaGroupModel) -> Unit = { it.stopAutomation() }
    val moveToDown: (rootSchemaGroupModel: RootSchemaGroupModel) -> Unit = { it.selectedModel.downActionBlock() }
    val moveToUp: (rootSchemaGroupModel: RootSchemaGroupModel) -> Unit = { it.selectedModel.upActionBlock() }
    val remove: (rootSchemaGroupModel: RootSchemaGroupModel) -> Unit = { it.removeSelectedModel() }

}