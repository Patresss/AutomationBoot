package com.patres.automation.gui.menuItem

import com.patres.automation.gui.controller.model.RootSchemaGroupController

object MenuItemValidators {
    val isNotSelectedActionOrIsRoot: (controller: RootSchemaGroupController) -> Boolean = { it.schemaGroupController == it.selectedModel }
}