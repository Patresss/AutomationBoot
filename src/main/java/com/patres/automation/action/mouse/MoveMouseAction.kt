package com.patres.automation.action.mouse


import com.patres.automation.action.mouse.point.PointDetector
import com.patres.automation.gui.controller.box.AbstractBox
import com.patres.automation.gui.controller.model.AutomationController
import com.patres.automation.type.ActionBootMousePoint

class MoveMouseAction(
        pointDetector: PointDetector,
        box: AbstractBox<*>?
) : MousePointAction(pointDetector, ActionBootMousePoint.MOVE_MOUSE, box) {

    override fun runMouseAction() {
        // just move
    }

}
