package com.patres.automation.action.mouse.doubleClick

import com.patres.automation.menuItem.MenuItem
import com.patres.automation.model.RootSchemaGroupModel
import com.patres.automation.model.SchemaGroupModel
import com.sun.glass.ui.Robot.MOUSE_RIGHT_BTN

class RightDoubleMouseClickAction(
        root: RootSchemaGroupModel,
        parent: SchemaGroupModel
) : MouseDoubleClickAction(root, parent) {

    init {
        controller.actionLabel.text = MenuItem.DOUBLE_CLICK_RIGHT_MOUSE_BUTTON.actionName
    }

    override val buttonBit: Int = MOUSE_RIGHT_BTN

}
