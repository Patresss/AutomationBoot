package com.patres.automation.gui.menuItem

import com.patres.automation.action.RootSchemaGroupModel
import com.patres.automation.gui.controller.box.SchemaGroupController

object MenuItemHandlers {

    val runAutomation: (rootSchemaGroupModel: RootSchemaGroupModel) -> Unit = { it.runAutomation() }
    val stopAutomation: (rootSchemaGroupModel: RootSchemaGroupModel) -> Unit = { it.stopAutomation() }
    val moveToDown: (rootSchemaGroupModel: RootSchemaGroupModel) -> Unit = { it.controller.selectedModel.downActionBlock() }
    val moveToUp: (rootSchemaGroupModel: RootSchemaGroupModel) -> Unit = { it.controller.selectedModel.upActionBlock() }
    val remove: (rootSchemaGroupModel: RootSchemaGroupModel) -> Unit = { it.controller.removeSelectedModel() }
    val addGroup: (rootSchemaGroupModel: RootSchemaGroupModel) -> Unit = { it.controller.addActionBlocks(SchemaGroupController()) }

}