package com.patres.languagepopup.action.mouse.click

import com.patres.languagepopup.gui.controller.model.TextActionController
import com.patres.languagepopup.model.RootSchemaGroupModel
import com.patres.languagepopup.model.SchemaGroupModel

import com.sun.glass.ui.Robot.MOUSE_RIGHT_BTN

class RightMouseClickAction(
        controller: TextActionController,
        root: RootSchemaGroupModel,
        parent: SchemaGroupModel?
) : MouseClickAction(controller, root, parent) {

    override val buttonBit: Int
        get() = MOUSE_RIGHT_BTN

}
