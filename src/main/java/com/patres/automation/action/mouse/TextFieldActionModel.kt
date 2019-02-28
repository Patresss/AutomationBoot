package com.patres.automation.action.mouse

import com.patres.automation.gui.controller.model.TextFieldActionController
import com.patres.automation.model.ActionNodeModel
import com.patres.automation.model.RootSchemaGroupModel
import com.patres.automation.model.SchemaGroupModel

abstract class TextFieldActionModel(
        root: RootSchemaGroupModel,
        parent: SchemaGroupModel
) : ActionNodeModel<TextFieldActionController>(root, parent) {

    fun getActionValue() = controller.value

    fun setActionValue(actionValue: String) {
        controller.value = actionValue
    }

}