package com.patres.automation.action.mouse.doubleClick

import com.patres.automation.menuItem.MenuItem
import com.patres.automation.model.RootSchemaGroupModel
import com.patres.automation.model.SchemaGroupModel
import java.awt.event.InputEvent

class MiddleDoubleMouseClickAction(
        root: RootSchemaGroupModel,
        parent: SchemaGroupModel
) : MouseDoubleClickAction(root, parent) {

    init {
        controller.actionLabel.text = MenuItem.DOUBLE_CLICK_MIDDLE_MOUSE_BUTTON.actionName
    }

    override val buttonBit: Int = InputEvent.BUTTON2_DOWN_MASK

}
