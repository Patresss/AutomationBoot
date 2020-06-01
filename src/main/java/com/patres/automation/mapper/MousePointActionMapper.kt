package com.patres.automation.mapper

import com.patres.automation.action.mouse.*
import com.patres.automation.action.mouse.point.ImagePointDetector
import com.patres.automation.action.mouse.point.PointDetector
import com.patres.automation.action.mouse.point.SpecificPointDetector
import com.patres.automation.excpetion.ApplicationException
import com.patres.automation.excpetion.CannotFindRootSchemaException
import com.patres.automation.gui.controller.model.MousePointActionController
import com.patres.automation.mapper.model.MousePointActionSerialized
import com.patres.automation.point.Point
import com.patres.automation.type.ActionBootMousePoint
import com.patres.automation.util.Base64Converter
import javafx.beans.property.BooleanProperty
import java.util.*


object MousePointActionMapper : Mapper<MousePointActionController, MousePointAction, MousePointActionSerialized> {

    override fun controllerToModel(controller: MousePointActionController): MousePointAction {
        val pointDetector = calculatePointDetector(controller)
        return createModel(controller.actionBoot, pointDetector)
    }

    override fun controllerToSerialized(controller: MousePointActionController): MousePointActionSerialized {
        return controller.run {
            MousePointActionSerialized(actionBoot, value, bytesArrayToBase64(calculateImageBytesArray()), thresholdSlider.value, ignoreIfNotFoundCheckBox.isSelected)
        }
    }

    override fun serializedToController(serialized: MousePointActionSerialized): MousePointActionController {
        return MousePointActionController(serialized.actionBootType).apply {
            value = serialized.point ?: ""
            serialized.image?.let {
                setImage(Base64Converter.base64ToInputStream(it))
                thresholdSlider.value = serialized.threshold ?: 90.0
                ignoreIfNotFoundCheckBox.isSelected = serialized.ignoreIfNotFound
            }
        }
    }

    override fun serializedToModel(serialized: MousePointActionSerialized, automationRunningProperty: BooleanProperty): MousePointAction {
        val pointDetector = calculatePointDetectorFromSerialized(serialized, automationRunningProperty)
        return createModel(serialized.actionBootType, pointDetector)
    }

    private fun createModel(actionType: ActionBootMousePoint, pointDetector: PointDetector): MousePointAction {
        return when (actionType) {
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


    private fun calculatePointDetector(controller: MousePointActionController): PointDetector {
        val calculatedImageBytesArray = controller.calculateImageBytesArray()
        return if (calculatedImageBytesArray != null) {
            val root = controller.root ?: throw CannotFindRootSchemaException()
            val automationRunningProperty = root.actionRunner.automationRunningProperty
            ImagePointDetector(calculatedImageBytesArray, controller.thresholdSlider.value, controller.ignoreIfNotFoundCheckBox.isSelected, automationRunningProperty)
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


    private fun calculatePointDetectorFromSerialized(serialized: MousePointActionSerialized, automationRunningProperty: BooleanProperty): PointDetector {
        return when {
            serialized.image != null -> {
                val templateByteArray: ByteArray = Base64Converter.base64ToByteArray(serialized.image)
                val threshold = serialized.threshold ?: 90.0
                ImagePointDetector(templateByteArray, threshold, serialized.ignoreIfNotFound, automationRunningProperty)
            }
            serialized.point != null -> {
                SpecificPointDetector(Point.stringToPoint(serialized.point))
            }
            else -> {
                throw ApplicationException("Point cannot be found")
            }
        }
    }

}
