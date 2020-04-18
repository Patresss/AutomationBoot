package com.patres.automation.mapper

import com.patres.automation.action.delay.DelayAction
import com.patres.automation.action.delay.DelayType
import com.patres.automation.gui.controller.model.DelayActionController
import com.patres.automation.mapper.model.DelayActionSerialized
import com.patres.automation.type.ActionBootDelay
import javafx.beans.property.BooleanProperty

object DelayActionMapper : Mapper<DelayActionController, DelayAction, DelayActionSerialized> {

    override fun controllerToModel(controller: DelayActionController): DelayAction {
        return calculateDelayActionModel(controller.actionBoot, controller.value.toLong(), controller.selectedDelayTime(), controller.root?.automationRunningProperty)

    }

    override fun controllerToSerialized(controller: DelayActionController): DelayActionSerialized {
        return controller.run {
            DelayActionSerialized(actionBoot, value, selectedDelayTime())
        }
    }

    override fun serializedToController(serialized: DelayActionSerialized): DelayActionController {
        return DelayActionController(serialized.actionBootType).apply {
            value = serialized.value
            comboBox.selectionModel.select(serialized.delayType)
        }
    }

    override fun serializedToModel(serialized: DelayActionSerialized, automationRunningProperty: BooleanProperty?): DelayAction {
        return calculateDelayActionModel(serialized.actionBootType, serialized.value.toLong(), serialized.delayType, automationRunningProperty)
    }

    private fun calculateDelayActionModel(actionType: ActionBootDelay, delay: Long, delayType: DelayType, automationRunningProperty: BooleanProperty?): DelayAction {
        return when (actionType) {
            ActionBootDelay.DELAY -> DelayAction(delay, automationRunningProperty, delayType)
        }
    }

}