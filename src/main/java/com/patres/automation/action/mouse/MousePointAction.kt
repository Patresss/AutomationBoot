package com.patres.automation.action.mouse

import com.patres.automation.point.Point
import com.patres.automation.gui.controller.model.MousePointActionController
import com.patres.automation.menuItem.MenuItem
import com.patres.automation.model.RootSchemaGroupModel
import com.patres.automation.model.SchemaGroupModel
import com.patres.automation.point.ImageToPointConverter
import com.patres.automation.validation.AbstractValidation
import com.patres.automation.validation.PointVectorValidation

abstract class MousePointAction(
        root: RootSchemaGroupModel,
        parent: SchemaGroupModel
) : MouseAction(root, parent) {

    companion object {
        private const val DELAY = 150L
    }

    final override val controller: MousePointActionController = MousePointActionController(this)

    override var validator: AbstractValidation? = PointVectorValidation(controller).also { it.activateControlListener() }

    var point: Point? = null

    init {
        controller.actionLabel.text = MenuItem.DELAY.actionName
    }

    override fun runAction() {
        loadPoint()
        point?.let {
            robot.mouseMove(it.xPositionPointVector, it.yPositionPointVector)
        }
        runMouseAction()
        Thread.sleep(DELAY)
    }

    private fun loadPoint() {
        val image = controller.image
        point = if (image != null) {
            val pointInTheMiddle = ImageToPointConverter(image).calculatePointInTheMiddle()
            pointInTheMiddle
        } else {
            val pointString = getActionValue()
            Point.stringToPoint(pointString)
        }
    }



}
