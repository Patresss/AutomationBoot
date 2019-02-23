package com.patres.languagepopup.action.mouse.move


import com.patres.languagepopup.action.mouse.MousePointAction
import com.patres.languagepopup.menuItem.MenuItem
import com.patres.languagepopup.model.RootSchemaGroupModel
import com.patres.languagepopup.model.SchemaGroupModel
import com.sun.glass.ui.Robot

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
