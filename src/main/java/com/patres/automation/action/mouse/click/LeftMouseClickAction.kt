package com.patres.automation.action.mouse.click


import com.patres.automation.menuItem.MenuItem
import com.patres.automation.model.RootSchemaGroupModel
import com.patres.automation.model.SchemaGroupModel
import java.awt.event.InputEvent

class LeftMouseClickAction(
        root: RootSchemaGroupModel,
        parent: SchemaGroupModel
) : MouseClickAction(root, parent) {

    init {
        controller.actionLabel.text = MenuItem.CLICK_LEFT_MOUSE_BUTTON.actionName
    }

    override val buttonBit: Int = InputEvent.BUTTON1_DOWN_MASK

}
