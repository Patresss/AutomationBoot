package com.patres.languagepopup.action.mouse.click

import com.patres.languagepopup.gui.controller.model.TextActionController
import com.patres.languagepopup.model.RootSchemaGroupModel
import com.patres.languagepopup.model.SchemaGroupModel
import com.patres.languagepopup.model.TextActionModel

abstract class MouseClickAction(
        controller: TextActionController,
        root: RootSchemaGroupModel,
        parent: SchemaGroupModel?
) : TextActionModel(controller, root, parent) {

    abstract val buttonBit: Int

    fun run() {
        loadPoint()
        if (point != null) {
            robot.mouseMove(point!!.x, point!!.y)
        }

        robot.mousePress(buttonBit)
        robot.mouseRelease(buttonBit)
    }

}
