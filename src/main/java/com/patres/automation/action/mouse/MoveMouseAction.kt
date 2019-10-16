package com.patres.automation.action.mouse


import com.patres.automation.action.mouse.point.PointDetector
import com.patres.automation.type.ActionBootMousePoint

class MoveMouseAction(
        pointDetector: PointDetector
) : MousePointAction(pointDetector, ActionBootMousePoint.MOVE_MOUSE) {

    override fun runMouseAction() {
        // just move
    }

}
