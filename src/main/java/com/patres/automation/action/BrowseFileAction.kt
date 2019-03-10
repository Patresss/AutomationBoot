package com.patres.automation.action

import com.patres.automation.gui.controller.model.BrowseFileActionController
import com.patres.automation.model.RootSchemaGroupModel
import com.patres.automation.model.SchemaGroupModel
import com.patres.automation.validation.FileValidation

abstract class BrowseFileAction(
        root: RootSchemaGroupModel,
        parent: SchemaGroupModel
) : TextFieldActionModel<BrowseFileActionController>(root, parent) {

    override val controller: BrowseFileActionController = BrowseFileActionController(this)

    private var validation = FileValidation(controller).also { it.activateControlListener() }

    init {
        validation.activateControlListener()
    }

}
