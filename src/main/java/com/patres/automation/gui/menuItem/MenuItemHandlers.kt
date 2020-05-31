package com.patres.automation.gui.menuItem

import com.patres.automation.gui.controller.box.SchemaGroupController
import com.patres.automation.gui.controller.model.RootSchemaGroupController

object MenuItemHandlers {

    val runAutomation: (rootSchemaGroupController: RootSchemaGroupController) -> Unit = { it.runAutomation() }
    val stopAutomation: (rootSchemaGroupController: RootSchemaGroupController) -> Unit = { it.stopAutomation() }
    val moveToDown: (rootSchemaGroupController: RootSchemaGroupController) -> Unit = { it.selectedModel.downActionBlock() }
    val moveToUp: (rootSchemaGroupController: RootSchemaGroupController) -> Unit = { it.selectedModel.upActionBlock() }
    val remove: (rootSchemaGroupController: RootSchemaGroupController) -> Unit = { it.removeSelectedModel() }
    val startRecord: (rootSchemaGroupController: RootSchemaGroupController) -> Unit = { it.startRecord() }
    val stopRecord: (rootSchemaGroupController: RootSchemaGroupController) -> Unit = { it.stopRecord() }
    val addGroup: (rootSchemaGroupController: RootSchemaGroupController) -> Unit = { it.addActionBlocks(SchemaGroupController()) }

}