package com.patres.languagepopup.model

import com.patres.languagepopup.Point
import com.patres.languagepopup.excpetion.ApplicationException
import com.patres.languagepopup.excpetion.PointFormatException
import com.patres.languagepopup.gui.controller.model.TextActionController
import com.patres.languagepopup.validation.PointValidation
import javafx.scene.Node

open class TextActionModel(
        controller: TextActionController,
        root: RootSchemaGroupModel,
        parent: SchemaGroupModel?
) : AutomationModel<TextActionController>(controller, root, parent) {

    var point: Point? = null
    var pointValidation: PointValidation? = null

    init {
        activeValidation()
    }

    @Throws(PointFormatException::class)
    protected fun loadPoint() {
        val pointString = controller.valueTextField.text ?: ""
        this.point = Point.stringToPoint(pointString)
    }

    private fun activeValidation() {
        pointValidation = PointValidation(controller.validLabel, controller.valueTextField)
        pointValidation?.activateControlListener()
    }

    @Throws(ApplicationException::class)
    fun checkValidation() {
        if (pointValidation?.isConditionFulfilled == false) {
            throw PointFormatException(controller.valueTextField.text)
        }
    }

    override fun getMainNode(): Node = controller.getMainOutsideNode()

    override fun getMainInsideNode(): Node = controller.getMainInsideNode()

}