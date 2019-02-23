package com.patres.automation.model

import com.patres.automation.gui.controller.model.AutomationController

abstract class ActionNodeModel<ControllerType : AutomationController>(
        root: RootSchemaGroupModel,
        parent: SchemaGroupModel?
) : AutomationModel<ControllerType>(root, parent)
