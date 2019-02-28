package com.patres.automation.serialize.model

import com.patres.automation.action.delay.DelayAction
import com.patres.automation.action.mouse.TextFieldActionModel
import com.patres.automation.action.mouse.click.LeftMouseClickAction
import com.patres.automation.action.mouse.click.MiddleMouseClickAction
import com.patres.automation.action.mouse.click.RightMouseClickAction
import com.patres.automation.action.mouse.doubleClick.LeftDoubleMouseClickAction
import com.patres.automation.action.mouse.doubleClick.MiddleDoubleMouseClickAction
import com.patres.automation.action.mouse.doubleClick.RightDoubleMouseClickAction
import com.patres.automation.action.mouse.move.MoveMouseAction
import com.patres.automation.action.mouse.press.PressLeftMouseAction
import com.patres.automation.action.mouse.press.PressMiddleMouseAction
import com.patres.automation.action.mouse.press.PressRightMouseAction
import com.patres.automation.action.mouse.release.ReleaseLeftMouseAction
import com.patres.automation.action.mouse.release.ReleaseMiddleMouseAction
import com.patres.automation.action.mouse.release.ReleaseRightMouseAction
import com.patres.automation.menuItem.MenuItem

enum class TextAction(
        val classValue: Class<out TextFieldActionModel>,
        val actionName: String

) {

    CLICK_LEFT_MOUSE_BUTTON(classValue = LeftMouseClickAction::class.java, actionName = MenuItem.CLICK_LEFT_MOUSE_BUTTON.name),
    CLICK_MIDDLE_MOUSE_BUTTON(classValue = MiddleMouseClickAction::class.java, actionName = MenuItem.CLICK_MIDDLE_MOUSE_BUTTON.name),
    CLICK_RIGHT_MOUSE_BUTTON(classValue = RightMouseClickAction::class.java, actionName = MenuItem.CLICK_RIGHT_MOUSE_BUTTON.name),
    DOUBLE_CLICK_LEFT_MOUSE_BUTTON(classValue = LeftDoubleMouseClickAction::class.java, actionName = MenuItem.DOUBLE_CLICK_LEFT_MOUSE_BUTTON.name),
    DOUBLE_CLICK_MIDDLE_MOUSE_BUTTON(classValue = MiddleDoubleMouseClickAction::class.java, actionName = MenuItem.DOUBLE_CLICK_MIDDLE_MOUSE_BUTTON.name),
    DOUBLE_CLICK_RIGHT_MOUSE_BUTTON(classValue = RightDoubleMouseClickAction::class.java, actionName = MenuItem.DOUBLE_CLICK_RIGHT_MOUSE_BUTTON.name),
    PRESS_LEFT_MOUSE_BUTTON(classValue = PressLeftMouseAction::class.java, actionName = MenuItem.PRESS_LEFT_MOUSE_BUTTON.name),
    PRESS_MIDDLE_MOUSE_BUTTON(classValue = PressMiddleMouseAction::class.java, actionName = MenuItem.PRESS_MIDDLE_MOUSE_BUTTON.name),
    PRESS_RIGHT_MOUSE_BUTTON(classValue = PressRightMouseAction::class.java, actionName = MenuItem.PRESS_RIGHT_MOUSE_BUTTON.name),
    RELEASE_LEFT_MOUSE_BUTTON(classValue = ReleaseLeftMouseAction::class.java, actionName = MenuItem.RELEASE_LEFT_MOUSE_BUTTON.name),
    RELEASE_MIDDLE_MOUSE_BUTTON(classValue = ReleaseMiddleMouseAction::class.java, actionName = MenuItem.RELEASE_MIDDLE_MOUSE_BUTTON.name),
    RELEASE_RIGHT_MOUSE_BUTTON(classValue = ReleaseRightMouseAction::class.java, actionName = MenuItem.RELEASE_RIGHT_MOUSE_BUTTON.name),
    DELAY(classValue = DelayAction::class.java, actionName = MenuItem.DELAY.name),
    MOVE_MOUSE(classValue = MoveMouseAction::class.java, actionName = MenuItem.MOVE_MOUSE.name),


}