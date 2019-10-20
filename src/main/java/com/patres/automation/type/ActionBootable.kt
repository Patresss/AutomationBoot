package com.patres.automation.type

import com.patres.automation.action.RootSchemaGroupModel
import com.patres.automation.gui.controller.model.AutomationController
import com.patres.automation.validation.Validationable


interface ActionBootable {

    fun bundleName(): String
    fun validation(): Validationable?
    fun createController(): () -> AutomationController<*>

    fun addController(): (RootSchemaGroupModel) -> Unit {
        return { root: RootSchemaGroupModel -> root.controller.addActionBlocks(createController().invoke()) }
    }

}
