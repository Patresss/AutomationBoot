package com.patres.languagepopup.action.delay

import com.patres.languagepopup.excpetion.DelayFormatException
import com.patres.languagepopup.gui.controller.model.TextFieldActionController
import com.patres.languagepopup.gui.dialog.ExceptionHandlerDialog
import com.patres.languagepopup.keyboard.GlobalKeyListener
import com.patres.languagepopup.menuItem.MenuItem
import com.patres.languagepopup.model.ActionNodeModel
import com.patres.languagepopup.model.RootSchemaGroupModel
import com.patres.languagepopup.model.SchemaGroupModel
import com.patres.languagepopup.util.LoaderFactory
import com.patres.languagepopup.validation.DelayValidation
import javafx.scene.Node
import org.slf4j.LoggerFactory

class DelayAction(
        root: RootSchemaGroupModel,
        parent: SchemaGroupModel
) : ActionNodeModel<TextFieldActionController>(root, parent) {

    companion object {
        private val LOGGER = LoggerFactory.getLogger(DelayAction::class.java)
        private const val DELAY_STEP = 100
    }

    override val controller: TextFieldActionController = LoaderFactory.createTextFieldActionController(this)

    private var validation = DelayValidation(controller.validLabel, controller.valueTextField)

    init {
        controller.actionLabel.text = MenuItem.DELAY.actionName
        validation.activateControlListener()
    }

    override fun runAction() {
        try {
            val delay = getDelay()
            var currentDelay = 0
            while (currentDelay <= delay && !GlobalKeyListener.isStop()) {
                currentDelay += DELAY_STEP
                Thread.sleep(DELAY_STEP.toLong())
            }
        } catch (e: InterruptedException) {
            LOGGER.error("InterruptedException: {}", e)
            val dialog = ExceptionHandlerDialog(e)
            dialog.show()
        }
    }

    private fun getDelay(): Int {
        val text = controller.value
        try {
            return Integer.parseInt(text)
        } catch (e: Exception) {
            throw DelayFormatException(text)
        }

    }

    override fun getMainNode(): Node = controller.getMainNode()

    override fun getMainInsideNode(): Node = controller.getMainInsideNode()

}
