package com.patres.automation.action.mouse

import com.patres.automation.Point
import com.patres.automation.excpetion.PointFormatException
import com.patres.automation.gui.controller.model.MousePointActionController
import com.patres.automation.model.RootSchemaGroupModel
import com.patres.automation.model.SchemaGroupModel
import com.patres.automation.util.LoaderFactory
import com.patres.automation.validation.PointValidation
import javafx.scene.Node

abstract class MousePointAction(
        root: RootSchemaGroupModel,
        parent: SchemaGroupModel
) : MouseAction(root, parent) {

    companion object {
        private const val DELAY = 150L
    }

    override val controller: MousePointActionController = LoaderFactory.createMousePointActionController(this)

    var point: Point? = null

    private var pointValidation = PointValidation(controller.validLabel, controller.valueText)

    init {
        pointValidation.activateControlListener()
    }

    override fun runAction() {
        loadPoint()
        point?.let {
            robot.mouseMove(it.x, it.y)
        }
        runMouseAction()
        Thread.sleep(DELAY)
    }

    private fun loadPoint() {
        val pointString = getActionValue()
        this.point = Point.stringToPoint(pointString)
    }

    override fun checkValidations() {
        if (!pointValidation.isConditionFulfilled) {
            throw PointFormatException(getActionValue())
        }
    }

}
