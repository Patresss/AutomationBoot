package com.patres.automation.action.mouse

import com.patres.automation.action.AbstractAction
import com.patres.automation.type.ActionBootTextField
import com.patres.automation.type.ActionBootable
import java.awt.Robot

class ScrollWheelDownAction(numberOfScrolls: Int) : ScrollWheelAction(numberOfScrolls, true, ActionBootTextField.SCROLL_WHEEL_DOWN)
class ScrollWheelUpAction(numberOfScrolls: Int) : ScrollWheelAction(numberOfScrolls, false, ActionBootTextField.SCROLL_WHEEL_UP)

abstract class ScrollWheelAction(
        val numberOfScrolls: Int,
        val down: Boolean,
        actionBoot: ActionBootable
) : AbstractAction(actionBoot) {

    companion object {
        val robot: Robot = Robot()
    }

    override fun runAction() {
        repeat(numberOfScrolls) {
            robot.mouseWheel(if (down) 1 else -1)
        }
    }

    override fun toStringLog() = "Action: `$actionBootType` | down: `$down`, numberOfScrolls: `$numberOfScrolls`"


}
