package com.patres.automation.action.delay

import com.patres.automation.action.AbstractAction
import com.patres.automation.gui.dialog.ExceptionHandlerDialog
import com.patres.automation.type.ActionBootTextField
import javafx.beans.property.BooleanProperty
import org.slf4j.LoggerFactory

class DelayAction(
        private val delay: Int,
        private val canBeRunningProperty: BooleanProperty?
) : AbstractAction(ActionBootTextField.DELAY) {

    companion object {
        private val LOGGER = LoggerFactory.getLogger(DelayAction::class.java)
        private const val DELAY_STEP = 100
    }

    override fun runAction() {
        try {
            var currentDelay = 0
            while (currentDelay <= delay && canBeRunningProperty?.get() != false) {
                currentDelay += DELAY_STEP
                Thread.sleep(DELAY_STEP.toLong())
            }
        } catch (e: InterruptedException) {
            LOGGER.error("InterruptedException: {}", e)
            val dialog = ExceptionHandlerDialog(e)
            dialog.show()
        }
    }

}
