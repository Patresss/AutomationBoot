package com.patres.automation.action.delay

import com.patres.automation.action.TextFieldActionModel
import com.patres.automation.gui.controller.model.TextFieldActionController
import com.patres.automation.gui.dialog.ExceptionHandlerDialog
import com.patres.automation.keyboard.GlobalKeyListener
import com.patres.automation.menuItem.MenuItem
import com.patres.automation.model.RootSchemaGroupModel
import com.patres.automation.model.SchemaGroupModel
import com.patres.automation.util.getInteger
import com.patres.automation.validation.AbstractValidation
import com.patres.automation.validation.IntegerValidation
import org.slf4j.LoggerFactory

class DelayAction(
        root: RootSchemaGroupModel,
        parent: SchemaGroupModel
) : TextFieldActionModel<TextFieldActionController>(root, parent) {

    companion object {
        private val LOGGER = LoggerFactory.getLogger(DelayAction::class.java)
        private const val DELAY_STEP = 100
    }


    override val controller: TextFieldActionController = TextFieldActionController(this)

    override var validator: AbstractValidation? = IntegerValidation(controller).also { it.activateControlListener() }

    init {
        controller.actionLabel.text = MenuItem.DELAY.actionName
        validator?.activateControlListener()
    }

    override fun runAction() {
        try {
            val delay = getActionValue().getInteger()
            var currentDelay = 0
            while (currentDelay <= delay && !GlobalKeyListener.isStop) {
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
