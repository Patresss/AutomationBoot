package com.patres.automation.action.delay

import com.patres.automation.action.AbstractAction
import com.patres.automation.gui.controller.box.AbstractBox
import com.patres.automation.gui.controller.model.AutomationController
import com.patres.automation.gui.dialog.LogManager
import com.patres.automation.type.ActionBootTime
import javafx.beans.property.BooleanProperty

class DelayAction(
        val timeContainer: TimeContainer,
        private val canBeRunningProperty: BooleanProperty?,
        box: AbstractBox<*>?
) : AbstractAction(ActionBootTime.DELAY, box) {

    companion object {
        private const val DELAY_STEP = 100
    }

    override fun runAction() {
        try {
            var currentDelay = 0
            val delayMilliseconds = timeContainer.calculateMilliseconds()
            while (currentDelay <= delayMilliseconds && canBeRunningProperty?.get() == true) {
                currentDelay += DELAY_STEP
                Thread.sleep(DELAY_STEP.toLong())
            }
        } catch (e: InterruptedException) {
            LogManager.showAndLogException(e)
        }
    }

    override fun toStringLog() = "Action: `$actionBootType` | timeContainer: `$timeContainer`"
}
