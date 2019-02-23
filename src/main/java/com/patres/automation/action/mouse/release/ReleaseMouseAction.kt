package com.patres.automation.action.mouse.release

import com.patres.automation.action.mouse.MousePointAction
import com.patres.automation.model.RootSchemaGroupModel
import com.patres.automation.model.SchemaGroupModel

abstract class ReleaseMouseAction(
        root: RootSchemaGroupModel,
        parent: SchemaGroupModel
) : MousePointAction(root, parent) {

    override fun runMouseAction() {
        robot.mouseRelease(buttonBit)
    }

}
