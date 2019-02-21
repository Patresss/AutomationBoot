package com.patres.languagepopup.model

import com.patres.languagepopup.gui.controller.model.TextActionController

abstract class TextActionModel(
        root: RootSchemaGroupModel,
        parent: SchemaGroupModel?
) : AutomationModel<TextActionController>(root, parent)
