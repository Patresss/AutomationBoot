package com.patres.languagepopup.action.mouse.doubleClick

import com.patres.languagepopup.menuItem.MenuItem
import com.patres.languagepopup.model.RootSchemaGroupModel
import com.patres.languagepopup.model.SchemaGroupModel
import com.sun.glass.ui.Robot.MOUSE_MIDDLE_BTN

class MiddleDoubleMouseClickAction(
        root: RootSchemaGroupModel,
        parent: SchemaGroupModel
) : MouseDoubleClickAction(root, parent) {


    init {
        controller.actionLabel.text = MenuItem.DOUBLE_CLICK_MIDDLE_MOUSE_BUTTON.actionName
    }

    override val buttonBit: Int
        get() = MOUSE_MIDDLE_BTN

}
