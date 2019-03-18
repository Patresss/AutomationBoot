package com.patres.automation.action.mouse.press

import com.patres.automation.menuItem.MenuItem
import com.patres.automation.model.RootSchemaGroupModel
import com.patres.automation.model.SchemaGroupModel
import java.awt.event.InputEvent

class PressMiddleMouseAction(
        root: RootSchemaGroupModel,
        parent: SchemaGroupModel
) : PressMouseAction(root, parent) {

    init {
        controller.actionLabel.text = MenuItem.PRESS_MIDDLE_MOUSE_BUTTON.actionName
    }

    override val buttonBit: Int = InputEvent.BUTTON2_DOWN_MASK

}
