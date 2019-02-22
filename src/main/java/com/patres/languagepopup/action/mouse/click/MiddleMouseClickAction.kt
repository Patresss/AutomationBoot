package com.patres.languagepopup.action.mouse.click

import com.patres.languagepopup.menuItem.MenuItem
import com.patres.languagepopup.model.RootSchemaGroupModel
import com.patres.languagepopup.model.SchemaGroupModel
import com.sun.glass.ui.Robot.MOUSE_MIDDLE_BTN

class MiddleMouseClickAction(
        root: RootSchemaGroupModel,
        parent: SchemaGroupModel
) : MouseClickAction(root, parent) {

    init {
        controller.actionLabel.text = MenuItem.CLICK_MIDDLE_MOUSE_BUTTON.actionName
    }

    override val buttonBit: Int
        get() = MOUSE_MIDDLE_BTN

}
