package com.patres.automation.mapper

import com.patres.automation.action.AbstractAction
import com.patres.automation.action.delay.DelayAction
import com.patres.automation.gui.controller.model.DelayActionController
import com.patres.automation.mapper.model.DelayActionSerialized
import com.patres.automation.type.ActionBootDelay

object DelayActionMapper : Mapper<DelayActionController, AbstractAction, DelayActionSerialized> {

    override fun controllerToModel(controller: DelayActionController): AbstractAction {
        return when (controller.action) {
            ActionBootDelay.DELAY -> DelayAction(controller.value.toLong(), controller.root?.automationRunningProperty, controller.selectedDelayTime())
        }
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

}