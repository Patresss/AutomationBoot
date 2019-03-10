package com.patres.automation.action

import com.patres.automation.gui.controller.model.TextActionController
import com.patres.automation.model.ActionNodeModel
import com.patres.automation.model.RootSchemaGroupModel
import com.patres.automation.model.SchemaGroupModel
import com.patres.automation.validation.AbstractValidation
import javafx.scene.Node

abstract class TextFieldActionModel<ControllerType : TextActionController>(
        root: RootSchemaGroupModel,
        parent: SchemaGroupModel
) : ActionNodeModel<ControllerType>(root, parent) {

    open var validator: AbstractValidation? = null

    fun getActionValue() = controller.value

    fun setActionValue(actionValue: String) {
        controller.value = actionValue
    }

    override fun getMainNode(): Node = controller.getMainNode()

    override fun getMainInsideNode(): Node = controller.getMainInsideNode()

}