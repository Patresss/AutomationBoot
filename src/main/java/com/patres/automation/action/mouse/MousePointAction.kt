package com.patres.automation.action.mouse

import com.patres.automation.action.AbstractAction
import com.patres.automation.action.mouse.point.PointDetector
import com.patres.automation.point.Point
import com.patres.automation.type.ActionBootMousePoint

abstract class MousePointAction(
        private val pointDetector: PointDetector,
        actionBoot: ActionBootMousePoint
) : AbstractAction(actionBoot) {

    companion object {
        private const val DELAY = 150L
    }

    override fun runAction() {
        val point = pointDetector.calculatePoint()
        point?.let {
            robot.mouseMove(point.xPositionPointVector, point.yPositionPointVector)
            runMouseAction()
            Thread.sleep(DELAY)
        }
    }

    abstract fun runMouseAction()

}
