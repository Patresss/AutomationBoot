package com.patres.automation.action.mouse

import com.patres.automation.gui.controller.model.AutomationController
import com.patres.automation.model.ActionNodeModel
import com.patres.automation.model.RootSchemaGroupModel
import com.patres.automation.model.SchemaGroupModel

abstract class MouseAction<ControllerType : AutomationController>(
        root: RootSchemaGroupModel,
        parent: SchemaGroupModel
) : ActionNodeModel<ControllerType>(root, parent) {

    abstract val buttonBit: Int

    override fun runAction() {
        runMouseAction()
    }

    abstract fun runMouseAction()

}
