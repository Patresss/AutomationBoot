package com.patres.automation.action.mouse.move


import com.patres.automation.action.mouse.MousePointAction
import com.patres.automation.menuItem.MenuItem
import com.patres.automation.model.RootSchemaGroupModel
import com.patres.automation.model.SchemaGroupModel

class MoveMouseAction(
        root: RootSchemaGroupModel,
        parent: SchemaGroupModel
) : MousePointAction(root, parent) {

    init {
        controller.actionLabel.text = MenuItem.MOVE_MOUSE.actionName
    }

    override fun runMouseAction() {
        // just move
    }

    override val buttonBit: Int = 0

}
