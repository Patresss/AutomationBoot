package com.patres.automation.action.mouse

import com.patres.automation.Point
import com.patres.automation.excpetion.PointFormatException
import com.patres.automation.gui.controller.model.MousePointActionController
import com.patres.automation.menuItem.MenuItem
import com.patres.automation.model.RootSchemaGroupModel
import com.patres.automation.model.SchemaGroupModel
import com.patres.automation.validation.AbstractValidation
import com.patres.automation.validation.IntegerValidation
import com.patres.automation.validation.PointValidation

abstract class MousePointAction(
        root: RootSchemaGroupModel,
        parent: SchemaGroupModel
) : MouseAction(root, parent) {

    companion object {
        private const val DELAY = 150L
    }


    override val controller: MousePointActionController = MousePointActionController(this)

    override var validator: AbstractValidation? = PointValidation(controller).also { it.activateControlListener() }

    var point: Point? = null

    init {
        controller.actionLabel.text = MenuItem.DELAY.actionName
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
        if (validator?.isConditionFulfilled() == false) {
            throw PointFormatException(getActionValue())
        }
    }

}
