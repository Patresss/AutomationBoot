package com.patres.automation.action

import com.patres.automation.gui.controller.model.TextActionController
import com.patres.automation.model.ActionNodeModel
import com.patres.automation.model.RootSchemaGroupModel
import com.patres.automation.model.SchemaGroupModel
import com.patres.automation.validation.AbstractValidation

abstract class TextActionModel<ControllerType : TextActionController>(
        root: RootSchemaGroupModel,
        parent: SchemaGroupModel
) : ActionNodeModel<ControllerType>(root, parent) {

    open var validator: AbstractValidation? = null

    fun getActionValue() = controller.value

    fun setActionValue(actionValue: String) {
        controller.value = actionValue
    }

    override fun checkValidations() {
        validator?.let {
            if (!it.isConditionFulfilled()) {
                it.throwException()
            }
        }
    }
}