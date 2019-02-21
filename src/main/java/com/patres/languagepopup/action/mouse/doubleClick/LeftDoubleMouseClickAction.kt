package com.patres.languagepopup.action.mouse.doubleClick


import com.patres.languagepopup.menuItem.MenuItem
import com.patres.languagepopup.model.RootSchemaGroupModel
import com.patres.languagepopup.model.SchemaGroupModel
import com.sun.glass.ui.Robot

class LeftDoubleMouseClickAction(
        root: RootSchemaGroupModel,
        parent: SchemaGroupModel
) : MouseDoubleClickAction(root, parent) {

    init {
        controller.actionLabel.text = MenuItem.DOUBLE_CLICK_LEFT_MOUSE_BUTTON.actionName
    }

    override val buttonBit: Int
        get() = Robot.MOUSE_LEFT_BTN

}
