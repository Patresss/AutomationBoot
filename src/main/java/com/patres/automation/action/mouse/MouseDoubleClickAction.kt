package com.patres.automation.action.mouse

import com.patres.automation.action.mouse.point.PointDetector
import com.patres.automation.type.ActionBootMousePoint
import java.awt.event.InputEvent

class LeftDoubleMouseClickAction(pointDetector: PointDetector) : MouseDoubleClickAction(InputEvent.BUTTON1_DOWN_MASK, pointDetector, ActionBootMousePoint.DOUBLE_CLICK_LEFT_MOUSE_BUTTON)
class MiddleDoubleMouseClickAction(pointDetector: PointDetector) : MouseDoubleClickAction(InputEvent.BUTTON2_DOWN_MASK, pointDetector, ActionBootMousePoint.DOUBLE_CLICK_MIDDLE_MOUSE_BUTTON)
class RightDoubleMouseClickAction(pointDetector: PointDetector) : MouseDoubleClickAction(InputEvent.BUTTON3_DOWN_MASK, pointDetector, ActionBootMousePoint.DOUBLE_CLICK_RIGHT_MOUSE_BUTTON)

abstract class MouseDoubleClickAction(
        private val buttonBit: Int,
        pointDetector: PointDetector,
        actionBoot: ActionBootMousePoint
) : MousePointAction(pointDetector, actionBoot) {

    override fun runMouseAction() {
        robot.mousePress(buttonBit)
        robot.mouseRelease(buttonBit)

        robot.mousePress(buttonBit)
        robot.mouseRelease(buttonBit)
    }

}
