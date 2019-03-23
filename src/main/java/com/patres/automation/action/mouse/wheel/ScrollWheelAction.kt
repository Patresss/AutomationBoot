package com.patres.automation.action.mouse.wheel

import com.patres.automation.action.mouse.MouseAction
import com.patres.automation.gui.controller.model.TextFieldActionController
import com.patres.automation.model.RootSchemaGroupModel
import com.patres.automation.model.SchemaGroupModel
import com.patres.automation.util.getInteger
import com.patres.automation.validation.IntegerValidation

abstract class ScrollWheelAction(
        root: RootSchemaGroupModel,
        parent: SchemaGroupModel
) : MouseAction(root, parent) {

    companion object {
        private const val DELAY = 10L
    }

    override val buttonBit: Int = 0

    final override val controller: TextFieldActionController = TextFieldActionController(this)

    private var validation = IntegerValidation(controller).also { it.activateControlListener() }

    init {
        validation.activateControlListener()
    }

    abstract fun scroll()

    override fun runMouseAction() {
        val numberOfScrolls = getActionValue().getInteger()
        repeat(numberOfScrolls) {
            scroll()
            Thread.sleep(DELAY)
        }
    }

}
