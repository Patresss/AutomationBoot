package com.patres.automation.action.mouse

import com.patres.automation.action.mouse.point.PointDetector
import com.patres.automation.gui.controller.box.AbstractBox
import com.patres.automation.gui.controller.model.AutomationController
import com.patres.automation.type.ActionBootMousePoint
import java.awt.event.InputEvent

class LeftDoubleMouseClickAction(pointDetector: PointDetector, box: AbstractBox<*>?) : MouseDoubleClickAction(InputEvent.BUTTON1_DOWN_MASK, pointDetector, ActionBootMousePoint.DOUBLE_CLICK_LEFT_MOUSE_BUTTON, box)
class MiddleDoubleMouseClickAction(pointDetector: PointDetector, box: AbstractBox<*>?) : MouseDoubleClickAction(InputEvent.BUTTON2_DOWN_MASK, pointDetector, ActionBootMousePoint.DOUBLE_CLICK_MIDDLE_MOUSE_BUTTON, box)
class RightDoubleMouseClickAction(pointDetector: PointDetector, box: AbstractBox<*>?) : MouseDoubleClickAction(InputEvent.BUTTON3_DOWN_MASK, pointDetector, ActionBootMousePoint.DOUBLE_CLICK_RIGHT_MOUSE_BUTTON, box)

abstract class MouseDoubleClickAction(
        private val buttonBit: Int,
        pointDetector: PointDetector,
        actionBoot: ActionBootMousePoint,
        box: AbstractBox<*>?
) : MousePointAction(pointDetector, actionBoot, box) {

    override fun runMouseAction() {
        robot.mousePress(buttonBit)
        robot.mouseRelease(buttonBit)

        robot.mousePress(buttonBit)
        robot.mouseRelease(buttonBit)
    }

}
