package com.patres.languagepopup.model

import com.patres.languagepopup.gui.controller.model.AutomationController

abstract class ActionNodeModel<ControllerType : AutomationController>(
        root: RootSchemaGroupModel,
        parent: SchemaGroupModel?
) : AutomationModel<ControllerType>(root, parent)
