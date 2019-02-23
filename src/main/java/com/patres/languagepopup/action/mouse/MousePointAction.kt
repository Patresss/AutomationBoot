package com.patres.languagepopup.action.mouse

import com.patres.languagepopup.Point
import com.patres.languagepopup.excpetion.PointFormatException
import com.patres.languagepopup.gui.controller.model.MousePointActionController
import com.patres.languagepopup.model.RootSchemaGroupModel
import com.patres.languagepopup.model.SchemaGroupModel
import com.patres.languagepopup.util.LoaderFactory
import com.patres.languagepopup.validation.PointValidation
import javafx.scene.Node

abstract class MousePointAction(
        root: RootSchemaGroupModel,
        parent: SchemaGroupModel
) : MouseAction<MousePointActionController>(root, parent) {

    companion object {
        private const val DELAY = 150L
    }

    override val controller: MousePointActionController = LoaderFactory.createMousePointActionController(this)

    var point: Point? = null

    private var pointValidation = PointValidation(controller.validLabel, controller.valueTextField)

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
        val pointString = controller.valueTextField.text ?: ""
        this.point = Point.stringToPoint(pointString)
    }

    override fun checkValidations() {
        if (!pointValidation.isConditionFulfilled) {
            throw PointFormatException(controller.valueTextField.text)
        }
    }

    override fun getMainNode(): Node = controller.getMainNode()

    override fun getMainInsideNode(): Node = controller.getMainInsideNode()


}
