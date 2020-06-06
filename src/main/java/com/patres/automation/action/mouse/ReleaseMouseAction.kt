package com.patres.automation.action.mouse

import com.patres.automation.action.mouse.point.PointDetector
import com.patres.automation.gui.controller.box.AbstractBox
import com.patres.automation.gui.controller.model.AutomationController
import com.patres.automation.type.ActionBootMousePoint
import java.awt.event.InputEvent

class ReleaseLeftMouseAction(pointDetector: PointDetector, box: AbstractBox<*>?) : ReleaseMouseAction(InputEvent.BUTTON1_DOWN_MASK, pointDetector, ActionBootMousePoint.RELEASE_LEFT_MOUSE_BUTTON, box)
class ReleaseMiddleMouseAction(pointDetector: PointDetector, box: AbstractBox<*>?) : ReleaseMouseAction(InputEvent.BUTTON2_DOWN_MASK, pointDetector, ActionBootMousePoint.RELEASE_MIDDLE_MOUSE_BUTTON, box)
class ReleaseRightMouseAction(pointDetector: PointDetector, box: AbstractBox<*>?) : ReleaseMouseAction(InputEvent.BUTTON3_DOWN_MASK, pointDetector, ActionBootMousePoint.RELEASE_RIGHT_MOUSE_BUTTON, box)

abstract class ReleaseMouseAction(
        private val buttonBit: Int,
        pointDetector: PointDetector,
        actionBoot: ActionBootMousePoint,
        box: AbstractBox<*>?
) : MousePointAction(pointDetector, actionBoot, box) {

    override fun runMouseAction() {
        robot.mouseRelease(buttonBit)
    }

}

