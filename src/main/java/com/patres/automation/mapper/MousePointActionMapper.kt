package com.patres.automation.mapper

import com.patres.automation.action.mouse.*
import com.patres.automation.action.mouse.point.ImagePointDetector
import com.patres.automation.action.mouse.point.PointDetector
import com.patres.automation.action.mouse.point.SpecificPointDetector
import com.patres.automation.excpetion.ApplicationException
import com.patres.automation.excpetion.CannotFindRootSchemaException
import com.patres.automation.gui.controller.box.AbstractBox
import com.patres.automation.gui.controller.model.MousePointActionController
import com.patres.automation.mapper.model.MousePointActionSerialized
import com.patres.automation.parameter.received.ReceivedParameterConverter
import com.patres.automation.parameter.sent.SentParameter
import com.patres.automation.point.Point
import com.patres.automation.type.ActionBootMousePoint
import com.patres.automation.util.Base64Converter
import javafx.beans.property.BooleanProperty
import java.util.*


object MousePointActionMapper : Mapper<MousePointActionController, MousePointAction, MousePointActionSerialized> {

    override fun controllerToModel(controller: MousePointActionController, automationRunningProperty: BooleanProperty, parameters: Set<SentParameter>): MousePointAction {
        val pointDetector = calculatePointDetector(controller, automationRunningProperty, parameters)
        return createModel(controller.actionBoot, pointDetector, controller.box)
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
                ignoreIfNotFoundCheckBox.isSelected = serialized.ignoreIfNotFound ?: true
            }
        }
    }

    override fun serializedToModel(serialized: MousePointActionSerialized, automationRunningProperty: BooleanProperty, parameters: Set<SentParameter>): MousePointAction {
        val pointDetector = calculatePointDetectorFromSerialized(serialized, automationRunningProperty, parameters)
        return createModel(serialized.actionBootType, pointDetector)
    }

    private fun createModel(actionType: ActionBootMousePoint, pointDetector: PointDetector, box: AbstractBox<*>? = null): MousePointAction {
        return when (actionType) {
            ActionBootMousePoint.MOVE_MOUSE -> MoveMouseAction(pointDetector, box)

            ActionBootMousePoint.CLICK_LEFT_MOUSE_BUTTON -> LeftMouseClickAction(pointDetector, box)
            ActionBootMousePoint.CLICK_MIDDLE_MOUSE_BUTTON -> MiddleMouseClickAction(pointDetector, box)
            ActionBootMousePoint.CLICK_RIGHT_MOUSE_BUTTON -> RightMouseClickAction(pointDetector, box)

            ActionBootMousePoint.DOUBLE_CLICK_LEFT_MOUSE_BUTTON -> LeftDoubleMouseClickAction(pointDetector, box)
            ActionBootMousePoint.DOUBLE_CLICK_MIDDLE_MOUSE_BUTTON -> MiddleDoubleMouseClickAction(pointDetector, box)
            ActionBootMousePoint.DOUBLE_CLICK_RIGHT_MOUSE_BUTTON -> RightDoubleMouseClickAction(pointDetector, box)

            ActionBootMousePoint.PRESS_LEFT_MOUSE_BUTTON -> PressLeftMouseAction(pointDetector, box)
            ActionBootMousePoint.PRESS_MIDDLE_MOUSE_BUTTON -> PressMiddleMouseAction(pointDetector, box)
            ActionBootMousePoint.PRESS_RIGHT_MOUSE_BUTTON -> PressRightMouseAction(pointDetector, box)

            ActionBootMousePoint.RELEASE_LEFT_MOUSE_BUTTON -> ReleaseLeftMouseAction(pointDetector, box)
            ActionBootMousePoint.RELEASE_MIDDLE_MOUSE_BUTTON -> ReleaseMiddleMouseAction(pointDetector, box)
            ActionBootMousePoint.RELEASE_RIGHT_MOUSE_BUTTON -> ReleaseRightMouseAction(pointDetector, box)
        }
    }


    private fun calculatePointDetector(controller: MousePointActionController, automationRunningProperty: BooleanProperty, parameters: Set<SentParameter>): PointDetector {
        val calculatedImageBytesArray = controller.calculateImageBytesArray()
        return if (calculatedImageBytesArray != null) {
            ImagePointDetector(calculatedImageBytesArray, controller.thresholdSlider.value, controller.ignoreIfNotFoundCheckBox.isSelected, automationRunningProperty)
        } else {
            SpecificPointDetector(Point.stringToPoint(controller.calculateParametrizedValue(parameters)))
        }
    }

    private fun bytesArrayToBase64(bytes: ByteArray?): String? {
        bytes?.let {
            return Base64.getEncoder().encodeToString(bytes)
        }
        return null
    }


    private fun calculatePointDetectorFromSerialized(serialized: MousePointActionSerialized, automationRunningProperty: BooleanProperty, parameters: Set<SentParameter>): PointDetector {
        return when {
            serialized.image != null -> {
                val templateByteArray: ByteArray = Base64Converter.base64ToByteArray(serialized.image)
                val threshold = serialized.threshold ?: 90.0
                val ignoreIfNotFound = serialized.ignoreIfNotFound ?: true
                ImagePointDetector(templateByteArray, threshold, ignoreIfNotFound, automationRunningProperty)
            }
            serialized.point != null -> {
                val parametrizedValue = ReceivedParameterConverter(serialized.point, parameters).replaceValue()
                SpecificPointDetector(Point.stringToPoint(parametrizedValue))
            }
            else -> {
                throw ApplicationException("Point cannot be found")
            }
        }
    }

}
