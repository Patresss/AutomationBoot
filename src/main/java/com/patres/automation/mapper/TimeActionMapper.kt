package com.patres.automation.mapper

import com.patres.automation.action.delay.DelayAction
import com.patres.automation.action.delay.TimeContainer
import com.patres.automation.excpetion.CannotFindRootSchemaException
import com.patres.automation.excpetion.ControllerCannotBeMapToModelException
import com.patres.automation.gui.controller.model.TimeActionController
import com.patres.automation.mapper.model.TimeActionSerialized
import com.patres.automation.type.ActionBootTime
import com.patres.automation.util.extension.toLongOrZero
import javafx.beans.property.BooleanProperty
import javafx.beans.property.SimpleBooleanProperty

object TimeActionMapper : Mapper<TimeActionController, DelayAction, TimeActionSerialized> {

    override fun controllerToModel(controller: TimeActionController): DelayAction {
        val timeContainer = TimeContainer(controller.value.toLong(), controller.selectedDelayTime())
        println("=============== controllerToModel")
        println("=============== ${controller.root}")
        val root = controller.root?.automationRunningProperty ?: SimpleBooleanProperty(false)
        return calculateDelayActionModel(controller.actionBoot, timeContainer, root)

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

    private fun calculateDelayActionModel(actionType: ActionBootTime, timeContainer: TimeContainer, automationRunningProperty: BooleanProperty): DelayAction {
        return when (actionType) {
            ActionBootTime.DELAY -> DelayAction(timeContainer, automationRunningProperty)
            ActionBootTime.ADDITIONAL_DELAY_BETWEEN_ACTIONS -> throw ControllerCannotBeMapToModelException(actionType)
        }
    }

}