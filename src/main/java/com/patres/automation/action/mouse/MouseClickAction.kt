package com.patres.automation.action.mouse

import com.patres.automation.action.mouse.point.PointDetector
import com.patres.automation.gui.controller.box.AbstractBox
import com.patres.automation.gui.controller.model.AutomationController
import com.patres.automation.type.ActionBootMousePoint
import java.awt.event.InputEvent

class LeftMouseClickAction(pointDetector: PointDetector, box: AbstractBox<*>?) : MouseClickAction(InputEvent.BUTTON1_DOWN_MASK, pointDetector, ActionBootMousePoint.CLICK_LEFT_MOUSE_BUTTON, box)
class MiddleMouseClickAction(pointDetector: PointDetector, box: AbstractBox<*>?) : MouseClickAction(InputEvent.BUTTON2_DOWN_MASK, pointDetector, ActionBootMousePoint.CLICK_MIDDLE_MOUSE_BUTTON, box)
class RightMouseClickAction(pointDetector: PointDetector, box: AbstractBox<*>?) : MouseClickAction(InputEvent.BUTTON3_DOWN_MASK, pointDetector, ActionBootMousePoint.CLICK_RIGHT_MOUSE_BUTTON, box)

abstract class MouseClickAction(
        private val buttonBit: Int,
        pointDetector: PointDetector,
        actionBoot: ActionBootMousePoint,
        box: AbstractBox<*>?
) : MousePointAction(pointDetector, actionBoot, box) {

    override fun runMouseAction() {
        robot.mousePress(buttonBit)
        robot.mouseRelease(buttonBit)
    }

}
