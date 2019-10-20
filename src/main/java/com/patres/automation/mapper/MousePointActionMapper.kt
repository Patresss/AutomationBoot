package com.patres.automation.mapper

import com.patres.automation.action.mouse.*
import com.patres.automation.action.mouse.point.ImagePointDetector
import com.patres.automation.action.mouse.point.PointDetector
import com.patres.automation.action.mouse.point.SpecificPointDetector
import com.patres.automation.gui.controller.model.MousePointActionController
import com.patres.automation.mapper.model.MousePointActionSerialized
import com.patres.automation.point.Point
import com.patres.automation.type.ActionBootMousePoint
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.util.*


object MousePointActionMapper : Mapper<MousePointActionController, MousePointAction, MousePointActionSerialized> {

    override fun controllerToModel(controller: MousePointActionController): MousePointAction {
        val pointDetector = calculatePointDetector(controller)
        return when (controller.action) {
            ActionBootMousePoint.MOVE_MOUSE -> MoveMouseAction(pointDetector)

            ActionBootMousePoint.CLICK_LEFT_MOUSE_BUTTON -> LeftMouseClickAction(pointDetector)
            ActionBootMousePoint.CLICK_MIDDLE_MOUSE_BUTTON -> MiddleMouseClickAction(pointDetector)
            ActionBootMousePoint.CLICK_RIGHT_MOUSE_BUTTON -> RightMouseClickAction(pointDetector)

            ActionBootMousePoint.DOUBLE_CLICK_LEFT_MOUSE_BUTTON -> LeftDoubleMouseClickAction(pointDetector)
            ActionBootMousePoint.DOUBLE_CLICK_MIDDLE_MOUSE_BUTTON -> MiddleDoubleMouseClickAction(pointDetector)
            ActionBootMousePoint.DOUBLE_CLICK_RIGHT_MOUSE_BUTTON -> RightDoubleMouseClickAction(pointDetector)

            ActionBootMousePoint.PRESS_LEFT_MOUSE_BUTTON -> PressLeftMouseAction(pointDetector)
            ActionBootMousePoint.PRESS_MIDDLE_MOUSE_BUTTON -> PressMiddleMouseAction(pointDetector)
            ActionBootMousePoint.PRESS_RIGHT_MOUSE_BUTTON -> PressRightMouseAction(pointDetector)

            ActionBootMousePoint.RELEASE_LEFT_MOUSE_BUTTON -> ReleaseLeftMouseAction(pointDetector)
            ActionBootMousePoint.RELEASE_MIDDLE_MOUSE_BUTTON -> ReleaseMiddleMouseAction(pointDetector)
            ActionBootMousePoint.RELEASE_RIGHT_MOUSE_BUTTON -> ReleaseRightMouseAction(pointDetector)
        }
    }

    override fun controllerToSerialized(controller: MousePointActionController): MousePointActionSerialized {
        return controller.run {
            MousePointActionSerialized(action, value, bytesArrayToBase64(calculateImageBytesArray()), thresholdSlider.value)
        }
    }

    override fun serializedToController(serialized: MousePointActionSerialized): MousePointActionController {
        return MousePointActionController(serialized.actionType).apply {
            value = serialized.point ?: ""
            serialized.image?.let {
                setImage(base64ToInputStream(it))
                thresholdSlider.value = serialized.threshold ?: 90.0
            }
        }
    }

    private fun calculatePointDetector(controller: MousePointActionController): PointDetector {
        val calculatedImageBytesArray = controller.calculateImageBytesArray()
        return if (calculatedImageBytesArray != null) {
            ImagePointDetector(calculatedImageBytesArray, controller.thresholdSlider.value)
        } else {
            SpecificPointDetector(Point.stringToPoint(controller.value))
        }
    }

    private fun bytesArrayToBase64(bytes: ByteArray?): String? {
        bytes?.let {
            return Base64.getEncoder().encodeToString(bytes)
        }
        return null
    }

    private fun base64ToInputStream(string: String): InputStream {
        return ByteArrayInputStream(Base64.getDecoder().decode(string))
    }

}
