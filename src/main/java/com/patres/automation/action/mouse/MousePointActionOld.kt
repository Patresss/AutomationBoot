package com.patres.automation.action.mouse

import com.patres.automation.gui.controller.model.SchemaGroupController
import com.patres.automation.model.RootSchemaGroupModel

abstract class MousePointActionOld(
        root: RootSchemaGroupModel,
        parent: SchemaGroupController
) {

    companion object {
        private const val DELAY = 150L
    }

//    final  val controller: MousePointActionController = MousePointActionController(this)

//    override var validator: AbstractValidation? = PointVectorValidation(controller).also { it.activateControlListener() }
//
//    fun setImageInputStream(image: InputStream) {
//        controller.setImage(image)
//    }

//    override fun runAction() {
//       val point =  loadPoint()
//        point?.let {
//            robot.mouseMove(it.xPositionPointVector, it.yPositionPointVector)
//            runMouseAction()
//            Thread.sleep(DELAY)
//        }
//    }
//
//    private fun loadPoint(): Point? {
//        val image = controller.image
//        return if (image != null) {
//            val threshold = controller.thresholdSlider.value / 100.0
//            controller.calculateImageBytesArray()?.let {
//                ImageToPointConverter(image).calculatePointByTemplateMatchAndLogTime(it, threshold)
//            }
//        } else {
//            val pointString = getActionValue()
//            Point.stringToPoint(pointString)
//        }
//    }

}
