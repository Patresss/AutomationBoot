package com.patres.automation.action.mouse.release

import com.patres.automation.menuItem.MenuItem
import com.patres.automation.model.RootSchemaGroupModel
import com.patres.automation.model.SchemaGroupModel
import com.sun.glass.ui.Robot.MOUSE_RIGHT_BTN

class ReleaseRightMouseAction(
        root: RootSchemaGroupModel,
        parent: SchemaGroupModel
) : ReleaseMouseAction(root, parent) {

    init {
        controller.actionLabel.text = MenuItem.RELEASE_RIGHT_MOUSE_BUTTON.actionName
    }

    override val buttonBit: Int = MOUSE_RIGHT_BTN

}
