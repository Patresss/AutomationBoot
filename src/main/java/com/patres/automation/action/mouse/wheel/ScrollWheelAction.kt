package com.patres.automation.action.mouse.wheel

import com.patres.automation.action.mouse.MouseAction
import com.patres.automation.gui.controller.model.TextFieldActionController
import com.patres.automation.model.RootSchemaGroupModel
import com.patres.automation.model.SchemaGroupModel
import com.patres.automation.util.LoaderFactory
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

    override val controller: TextFieldActionController = LoaderFactory.createTextFieldActionController(this)

    private var validation = IntegerValidation(controller.validLabel, controller.valueText)

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