package com.patres.automation.action.mouse.press


import com.patres.automation.menuItem.MenuItem
import com.patres.automation.model.RootSchemaGroupModel
import com.patres.automation.model.SchemaGroupModel
import com.sun.glass.ui.Robot

class PressLeftMouseAction(
        root: RootSchemaGroupModel,
        parent: SchemaGroupModel
) : PressMouseAction(root, parent) {

    init {
        controller.actionLabel.text = MenuItem.PRESS_LEFT_MOUSE_BUTTON.actionName
    }

    override val buttonBit: Int = Robot.MOUSE_LEFT_BTN

}
