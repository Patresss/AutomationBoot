package com.patres.languagepopup.action.mouse.click


import com.patres.languagepopup.menuItem.MenuItem
import com.patres.languagepopup.model.RootSchemaGroupModel
import com.patres.languagepopup.model.SchemaGroupModel
import com.sun.glass.ui.Robot

class LeftMouseClickAction(
        root: RootSchemaGroupModel,
        parent: SchemaGroupModel
) : MouseClickAction(root, parent) {

    init {
        controller.actionLabel.text = MenuItem.CLICK_LEFT_MOUSE_BUTTON.actionName
    }

    override val buttonBit: Int
        get() = Robot.MOUSE_LEFT_BTN

}
