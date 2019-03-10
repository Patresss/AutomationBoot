package com.patres.automation.action.mouse

import com.patres.automation.action.TextFieldActionModel
import com.patres.automation.gui.controller.model.MousePointActionController
import com.patres.automation.model.RootSchemaGroupModel
import com.patres.automation.model.SchemaGroupModel

abstract class MouseAction(
        root: RootSchemaGroupModel,
        parent: SchemaGroupModel
) : TextFieldActionModel<MousePointActionController>(root, parent) {

    abstract val buttonBit: Int

    override fun runAction() {
        runMouseAction()
    }

    abstract fun runMouseAction()

}
