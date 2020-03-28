package com.patres.automation.mapper

import com.patres.automation.action.AbstractAction
import com.patres.automation.action.delay.DelayAction
import com.patres.automation.action.delay.DelayType
import com.patres.automation.gui.controller.model.DelayActionController
import com.patres.automation.mapper.model.DelayActionSerialized
import com.patres.automation.type.ActionBootDelay
import javafx.beans.property.BooleanProperty

object DelayActionMapper : Mapper<DelayActionController, AbstractAction, DelayActionSerialized> {

    override fun controllerToModel(controller: DelayActionController): AbstractAction {
        return calculateDelayActionModel(controller.action, controller.value.toLong(), controller.selectedDelayTime(), controller.root?.automationRunningProperty)

    }

    override fun controllerToSerialized(controller: DelayActionController): DelayActionSerialized {
        return controller.run {
            DelayActionSerialized(action, value, selectedDelayTime())
        }
    }

    override fun serializedToController(serialized: DelayActionSerialized): DelayActionController {
        return DelayActionController(serialized.actionType).apply {
            value = serialized.value
            comboBox.selectionModel.select(serialized.delayType)
        }
    }

    override fun serializedToModel(serialized: DelayActionSerialized, automationRunningProperty: BooleanProperty?): AbstractAction {
        return calculateDelayActionModel(serialized.actionType, serialized.value.toLong(), serialized.delayType, automationRunningProperty)
    }

    private fun calculateDelayActionModel(actionType: ActionBootDelay, delay: Long, delayType: DelayType, automationRunningProperty: BooleanProperty?): AbstractAction {
        return when (actionType) {
            ActionBootDelay.DELAY -> DelayAction(delay, automationRunningProperty, delayType)
        }
    }

}