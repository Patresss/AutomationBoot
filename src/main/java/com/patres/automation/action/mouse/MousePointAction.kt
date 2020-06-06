package com.patres.automation.action.mouse

import com.patres.automation.action.AbstractAction
import com.patres.automation.action.mouse.point.PointDetector
import com.patres.automation.gui.controller.box.AbstractBox
import com.patres.automation.gui.controller.model.AutomationController
import com.patres.automation.type.ActionBootMousePoint

abstract class MousePointAction(
        val pointDetector: PointDetector,
        actionBoot: ActionBootMousePoint,
        box: AbstractBox<*>?
) : AbstractAction(actionBoot, box) {

    override fun runAction() {
        val point = pointDetector.calculatePoint()
        point?.let {
            robot.mouseMove(point.xPositionPointVector, point.yPositionPointVector)
            runMouseAction()
        }
    }

    abstract fun runMouseAction()

    override fun toStringLog() = "Action: `$actionBootType` | Point: $pointDetector"

}
