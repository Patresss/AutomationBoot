package com.patres.automation.action.mouse

import com.patres.automation.action.mouse.point.PointDetector
import com.patres.automation.gui.controller.box.AbstractBox
import com.patres.automation.gui.controller.model.AutomationController
import com.patres.automation.type.ActionBootMousePoint
import java.awt.event.InputEvent


class PressLeftMouseAction(pointDetector: PointDetector, box: AbstractBox<*>?) : PressMouseAction(InputEvent.BUTTON1_DOWN_MASK, pointDetector, ActionBootMousePoint.PRESS_LEFT_MOUSE_BUTTON, box)
class PressMiddleMouseAction(pointDetector: PointDetector, box: AbstractBox<*>?) : PressMouseAction(InputEvent.BUTTON2_DOWN_MASK, pointDetector, ActionBootMousePoint.PRESS_MIDDLE_MOUSE_BUTTON, box)
class PressRightMouseAction(pointDetector: PointDetector, box: AbstractBox<*>?) : PressMouseAction(InputEvent.BUTTON3_DOWN_MASK, pointDetector, ActionBootMousePoint.PRESS_RIGHT_MOUSE_BUTTON, box)

abstract class PressMouseAction(
        private val buttonBit: Int,
        pointDetector: PointDetector,
        actionBoot: ActionBootMousePoint,
        box: AbstractBox<*>?
) : MousePointAction(pointDetector, actionBoot, box) {

    override fun runMouseAction() {
        robot.mousePress(buttonBit)
    }

}

