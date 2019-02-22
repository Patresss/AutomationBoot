package com.patres.languagepopup.action.mouse.release

import com.patres.languagepopup.action.mouse.MouseLabelAction
import com.patres.languagepopup.model.RootSchemaGroupModel
import com.patres.languagepopup.model.SchemaGroupModel

abstract class ReleaseMouseAction(
        root: RootSchemaGroupModel,
        parent: SchemaGroupModel
) : MouseLabelAction(root, parent) {

    override fun runMouseAction() {
        robot.mouseRelease(buttonBit)
    }

}
