package com.patres.automation.action.mouse

import com.patres.automation.action.mouse.point.PointDetector
import com.patres.automation.type.ActionBootMousePoint
import java.awt.event.InputEvent

class ReleaseLeftMouseAction(pointDetector: PointDetector) : ReleaseMouseAction(InputEvent.BUTTON1_DOWN_MASK, pointDetector, ActionBootMousePoint.RELEASE_LEFT_MOUSE_BUTTON)
class ReleaseMiddleMouseAction(pointDetector: PointDetector) : ReleaseMouseAction(InputEvent.BUTTON2_DOWN_MASK, pointDetector, ActionBootMousePoint.RELEASE_MIDDLE_MOUSE_BUTTON)
class ReleaseRightMouseAction(pointDetector: PointDetector) : ReleaseMouseAction(InputEvent.BUTTON3_DOWN_MASK, pointDetector, ActionBootMousePoint.RELEASE_RIGHT_MOUSE_BUTTON)

abstract class ReleaseMouseAction(
        private val buttonBit: Int,
        pointDetector: PointDetector,
        actionBoot: ActionBootMousePoint
) : MousePointAction(pointDetector, actionBoot) {

    override fun runMouseAction() {
        robot.mouseRelease(buttonBit)
    }

}

