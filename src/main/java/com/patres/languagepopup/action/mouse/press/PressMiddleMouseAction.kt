package com.patres.languagepopup.action.mouse.press

import com.patres.languagepopup.menuItem.MenuItem
import com.patres.languagepopup.model.RootSchemaGroupModel
import com.patres.languagepopup.model.SchemaGroupModel
import com.sun.glass.ui.Robot.MOUSE_MIDDLE_BTN

class PressMiddleMouseAction(
        root: RootSchemaGroupModel,
        parent: SchemaGroupModel
) : PressMouseAction(root, parent) {

    init {
        controller.actionLabel.text = MenuItem.PRESS_MIDDLE_MOUSE_BUTTON.actionName
    }

    override val buttonBit: Int
        get() = MOUSE_MIDDLE_BTN

}
