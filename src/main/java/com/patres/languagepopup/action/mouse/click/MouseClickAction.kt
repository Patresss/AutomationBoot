package com.patres.languagepopup.action.mouse.click

import com.patres.languagepopup.Point
import com.patres.languagepopup.menuItem.MenuItem
import com.patres.languagepopup.excpetion.PointFormatException
import com.patres.languagepopup.gui.controller.model.TextActionController
import com.patres.languagepopup.model.RootSchemaGroupModel
import com.patres.languagepopup.model.SchemaGroupModel
import com.patres.languagepopup.model.TextActionModel
import com.patres.languagepopup.util.LoaderFactory
import com.patres.languagepopup.validation.PointValidation
import javafx.scene.Node

abstract class MouseClickAction(
        root: RootSchemaGroupModel,
        parent: SchemaGroupModel
) : TextActionModel(root, parent) {

    final override val controller: TextActionController = LoaderFactory.createTextActionController(root, parent, MenuItem.CLICK_LEFT_MOUSE_BUTTON).also { it.model = this }

    abstract val buttonBit: Int

    var point: Point? = null

    private var pointValidation = PointValidation(controller.validLabel, controller.valueTextField)

    init {
        pointValidation.activateControlListener()
    }

    override fun runAction() {
        loadPoint()
        if (point != null) {
            robot.mouseMove(point!!.x, point!!.y)
        }

        robot.mousePress(buttonBit)
        robot.mouseRelease(buttonBit)
    }

    private fun loadPoint() {
        val pointString = controller.valueTextField.text ?: ""
        this.point = Point.stringToPoint(pointString)
    }


    override fun checkValidations() {
        if (!pointValidation.isConditionFulfilled) {
            throw PointFormatException(controller.valueTextField.text)
        }
    }

    override fun getMainNode(): Node = controller.getMainOutsideNode()

    override fun getMainInsideNode(): Node = controller.getMainInsideNode()


}
