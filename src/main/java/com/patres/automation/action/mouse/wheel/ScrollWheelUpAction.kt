package com.patres.automation.action.mouse.wheel

import com.patres.automation.menuItem.MenuItem
import com.patres.automation.model.RootSchemaGroupModel
import com.patres.automation.model.SchemaGroupModel

class ScrollWheelUpAction(
        root: RootSchemaGroupModel,
        parent: SchemaGroupModel
) : ScrollWheelAction(root, parent) {

    init {
        controller.actionLabel.text = MenuItem.SCROLL_WHEEL_UP.actionName
    }

    override fun scroll() {
        robot.mouseWheel(-1)
    }

}
