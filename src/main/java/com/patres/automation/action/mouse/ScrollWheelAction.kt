package com.patres.automation.action.mouse

import com.patres.automation.action.AbstractAction
import com.patres.automation.type.ActionBootTextField
import com.patres.automation.type.ActionBootable
import java.awt.Robot

class ScrollWheelDownAction(numberOfScrolls: Int) : ScrollWheelAction(numberOfScrolls, true, ActionBootTextField.SCROLL_WHEEL_DOWN)
class ScrollWheelUpAction(numberOfScrolls: Int) : ScrollWheelAction(numberOfScrolls, false, ActionBootTextField.SCROLL_WHEEL_UP)

abstract class ScrollWheelAction(
        private val numberOfScrolls: Int,
        private val down: Boolean,
        actionBoot: ActionBootable
) : AbstractAction(actionBoot) {

    companion object {
        val robot: Robot = Robot()
        private const val DELAY = 10L

    }

    override fun runAction() {
        repeat(numberOfScrolls) {
            robot.mouseWheel(if (down) 1 else -1)
            Thread.sleep(DELAY)
        }
    }

    override fun toStringLog() = "Action: `$actionBoot` | down: `$down`, numberOfScrolls: `$numberOfScrolls`"


}
