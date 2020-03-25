package com.patres.automation.action.delay

import com.patres.automation.action.AbstractAction
import com.patres.automation.gui.dialog.LogManager
import com.patres.automation.type.ActionBootDelay
import javafx.beans.property.BooleanProperty

class DelayAction(
        private val delay: Long,
        private val canBeRunningProperty: BooleanProperty?,
        private val delayType: DelayType = DelayType.MILLISECONDS
) : AbstractAction(ActionBootDelay.DELAY) {

    companion object {
        private const val DELAY_STEP = 100
    }

    override fun runAction() {
        try {
            var currentDelay = 0
            val delayMilliseconds = delayType.toMilliseconds(delay)
            while (currentDelay <= delayMilliseconds && canBeRunningProperty?.get() == true) {
                currentDelay += DELAY_STEP
                Thread.sleep(DELAY_STEP.toLong())
            }
        } catch (e: InterruptedException) {
            LogManager.showAndLogException(e)
        }
    }

    override fun toStringLog() = "Action: `$actionBoot` | delay: `$delay`, delayType: `$delayType`"
}
