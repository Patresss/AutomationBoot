package com.patres.languagepopup.action.mouse.release


import com.patres.languagepopup.menuItem.MenuItem
import com.patres.languagepopup.model.RootSchemaGroupModel
import com.patres.languagepopup.model.SchemaGroupModel
import com.sun.glass.ui.Robot

class ReleaseLeftMouseAction(
        root: RootSchemaGroupModel,
        parent: SchemaGroupModel
) : ReleaseMouseAction(root, parent) {

    init {
        controller.actionLabel.text = MenuItem.RELEASE_LEFT_MOUSE_BUTTON.actionName
    }

    override val buttonBit: Int
        get() = Robot.MOUSE_LEFT_BTN

}
