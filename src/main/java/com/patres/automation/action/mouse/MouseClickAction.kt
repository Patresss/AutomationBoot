package com.patres.automation.action.mouse

import com.patres.automation.action.mouse.point.PointDetector
import com.patres.automation.type.ActionBootMousePoint
import java.awt.event.InputEvent

class LeftMouseClickAction(pointDetector: PointDetector) : MouseClickAction(InputEvent.BUTTON1_DOWN_MASK, pointDetector, ActionBootMousePoint.CLICK_LEFT_MOUSE_BUTTON)
class MiddleMouseClickAction(pointDetector: PointDetector) : MouseClickAction(InputEvent.BUTTON2_DOWN_MASK, pointDetector, ActionBootMousePoint.CLICK_MIDDLE_MOUSE_BUTTON)
class RightMouseClickAction(pointDetector: PointDetector) : MouseClickAction(InputEvent.BUTTON3_DOWN_MASK, pointDetector, ActionBootMousePoint.CLICK_RIGHT_MOUSE_BUTTON)

abstract class MouseClickAction(
        private val buttonBit: Int,
        pointDetector: PointDetector,
        actionBoot: ActionBootMousePoint
) : MousePointAction(pointDetector, actionBoot) {

    override fun runMouseAction() {
        robot.mousePress(buttonBit)
        robot.mouseRelease(buttonBit)
    }

}
