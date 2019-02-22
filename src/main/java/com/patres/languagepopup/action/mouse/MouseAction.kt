package com.patres.languagepopup.action.mouse

import com.patres.languagepopup.gui.controller.model.AutomationController
import com.patres.languagepopup.model.ActionNodeModel
import com.patres.languagepopup.model.RootSchemaGroupModel
import com.patres.languagepopup.model.SchemaGroupModel

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
