package com.patres.automation.action.mouse.doubleClick

import com.patres.automation.action.mouse.MousePointAction
import com.patres.automation.model.RootSchemaGroupModel
import com.patres.automation.model.SchemaGroupModel

abstract class MouseDoubleClickAction(
        root: RootSchemaGroupModel,
        parent: SchemaGroupModel
) : MousePointAction(root, parent) {


    override fun runMouseAction() {
        robot.mousePress(buttonBit)
        robot.mouseRelease(buttonBit)

        robot.mousePress(buttonBit)
        robot.mouseRelease(buttonBit)
    }

}
