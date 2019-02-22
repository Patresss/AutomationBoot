package com.patres.languagepopup.action.mouse.press

import com.patres.languagepopup.action.mouse.MouseLabelAction
import com.patres.languagepopup.model.RootSchemaGroupModel
import com.patres.languagepopup.model.SchemaGroupModel

abstract class PressMouseAction(
        root: RootSchemaGroupModel,
        parent: SchemaGroupModel
) : MouseLabelAction(root, parent) {

    override fun runMouseAction() {
        robot.mousePress(buttonBit)
    }

}
