package com.patres.automation.action.mouse.press

import com.patres.automation.action.mouse.MousePointAction
import com.patres.automation.model.RootSchemaGroupModel
import com.patres.automation.model.SchemaGroupModel

abstract class PressMouseAction(
        root: RootSchemaGroupModel,
        parent: SchemaGroupModel
) : MousePointAction(root, parent) {

    override fun runMouseAction() {
        robot.mousePress(buttonBit)
    }

}
