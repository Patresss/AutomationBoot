package com.patres.automation.action.mouse

import com.patres.automation.action.mouse.point.PointDetector
import com.patres.automation.type.ActionBootMousePoint
import java.awt.event.InputEvent


class PressLeftMouseAction(pointDetector: PointDetector) : PressMouseAction(InputEvent.BUTTON1_DOWN_MASK, pointDetector, ActionBootMousePoint.PRESS_LEFT_MOUSE_BUTTON)
class PressMiddleMouseAction(pointDetector: PointDetector) : PressMouseAction(InputEvent.BUTTON2_DOWN_MASK, pointDetector, ActionBootMousePoint.PRESS_MIDDLE_MOUSE_BUTTON)
class PressRightMouseAction(pointDetector: PointDetector) : PressMouseAction(InputEvent.BUTTON3_DOWN_MASK, pointDetector, ActionBootMousePoint.PRESS_RIGHT_MOUSE_BUTTON)

abstract class PressMouseAction(
        private val buttonBit: Int,
        pointDetector: PointDetector,
        actionBoot: ActionBootMousePoint
) : MousePointAction(pointDetector, actionBoot) {

    override fun runMouseAction() {
        robot.mousePress(buttonBit)
    }

}

