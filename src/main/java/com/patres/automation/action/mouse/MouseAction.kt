package com.patres.automation.action.mouse

import com.patres.automation.model.RootSchemaGroupModel
import com.patres.automation.model.SchemaGroupModel

abstract class MouseAction(
        root: RootSchemaGroupModel,
        parent: SchemaGroupModel
) : TextFieldActionModel(root, parent) {

    abstract val buttonBit: Int

    override fun runAction() {
        runMouseAction()
    }

    abstract fun runMouseAction()

}
