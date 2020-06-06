package com.patres.automation.mapper

import com.patres.automation.action.delay.DelayAction
import com.patres.automation.action.delay.TimeContainer
import com.patres.automation.excpetion.ControllerCannotBeMapToModelException
import com.patres.automation.gui.controller.box.AbstractBox
import com.patres.automation.gui.controller.model.TimeActionController
import com.patres.automation.mapper.model.TimeActionSerialized
import com.patres.automation.type.ActionBootTime
import com.patres.automation.util.extension.toLongOrZero
import javafx.beans.property.BooleanProperty

object TimeActionMapper : Mapper<TimeActionController, DelayAction, TimeActionSerialized> {

    override fun controllerToModel(controller: TimeActionController, automationRunningProperty: BooleanProperty ): DelayAction {
        val timeContainer = TimeContainer(controller.value.toLong(), controller.selectedDelayTime())
        return calculateDelayActionModel(controller.actionBoot, timeContainer, automationRunningProperty, controller.box)
    }

    override fun controllerToSerialized(controller: TimeActionController): TimeActionSerialized {
        return controller.run {
            val timeContainer = TimeContainer(value.toLongOrZero(), selectedDelayTime())
            TimeActionSerialized(actionBoot, timeContainer)
        }
    }

    override fun serializedToController(serialized: TimeActionSerialized): TimeActionController {
        return TimeActionController(serialized.actionBootType).apply {
            value = serialized.timeContainer.value.toString()
            comboBox.selectionModel.select(serialized.timeContainer.type)
        }
    }

    override fun serializedToModel(serialized: TimeActionSerialized, automationRunningProperty: BooleanProperty): DelayAction {
        return calculateDelayActionModel(serialized.actionBootType, serialized.timeContainer, automationRunningProperty)
    }

    private fun calculateDelayActionModel(actionType: ActionBootTime, timeContainer: TimeContainer, automationRunningProperty: BooleanProperty, box: AbstractBox<*>? = null): DelayAction {
        return when (actionType) {
            ActionBootTime.DELAY -> DelayAction(timeContainer, automationRunningProperty, box)
            ActionBootTime.ADDITIONAL_DELAY_BETWEEN_ACTIONS -> throw ControllerCannotBeMapToModelException(actionType)
        }
    }

}