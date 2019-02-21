package com.patres.languagepopup.action.mouse.release

import com.patres.languagepopup.action.mouse.doubleClick.MouseDoubleClickAction
import com.patres.languagepopup.menuItem.MenuItem
import com.patres.languagepopup.model.RootSchemaGroupModel
import com.patres.languagepopup.model.SchemaGroupModel
import com.sun.glass.ui.Robot.MOUSE_RIGHT_BTN

class ReleaseRightMouseAction(
        root: RootSchemaGroupModel,
        parent: SchemaGroupModel
) : ReleaseMouseAction(root, parent) {

    init {
        controller.actionLabel.text = MenuItem.RELEASE_RIGHT_MOUSE_BUTTON.actionName
    }

    override val buttonBit: Int
        get() = MOUSE_RIGHT_BTN

}
