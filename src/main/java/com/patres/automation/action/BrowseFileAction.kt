package com.patres.automation.action

import com.patres.automation.gui.controller.model.BrowseFileActionController
import com.patres.automation.model.RootSchemaGroupModel
import com.patres.automation.model.SchemaGroupModel
import com.patres.automation.validation.FileExistValidation

abstract class BrowseFileAction(
        root: RootSchemaGroupModel,
        parent: SchemaGroupModel
) : TextActionModel<BrowseFileActionController>(root, parent) {

    override val controller: BrowseFileActionController = BrowseFileActionController(this)

}
