package com.patres.languagepopup.action.mouse.doubleClick

import com.patres.languagepopup.action.mouse.MouseAction
import com.patres.languagepopup.model.RootSchemaGroupModel
import com.patres.languagepopup.model.SchemaGroupModel

abstract class MouseDoubleClickAction(
        root: RootSchemaGroupModel,
        parent: SchemaGroupModel
) : MouseAction(root, parent) {

    override fun runMouseAction() {
        robot.mousePress(buttonBit)
        robot.mouseRelease(buttonBit)

        robot.mousePress(buttonBit)
        robot.mouseRelease(buttonBit)
    }

}
