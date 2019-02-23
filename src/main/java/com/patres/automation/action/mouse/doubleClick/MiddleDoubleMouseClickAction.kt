package com.patres.automation.action.mouse.doubleClick

import com.patres.automation.menuItem.MenuItem
import com.patres.automation.model.RootSchemaGroupModel
import com.patres.automation.model.SchemaGroupModel
import com.sun.glass.ui.Robot.MOUSE_MIDDLE_BTN

class MiddleDoubleMouseClickAction(
        root: RootSchemaGroupModel,
        parent: SchemaGroupModel
) : MouseDoubleClickAction(root, parent) {

    init {
        controller.actionLabel.text = MenuItem.DOUBLE_CLICK_MIDDLE_MOUSE_BUTTON.actionName
    }

    override val buttonBit: Int = MOUSE_MIDDLE_BTN

}
