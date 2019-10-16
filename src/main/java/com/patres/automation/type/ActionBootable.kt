package com.patres.automation.type

import com.patres.automation.ActionBootControllerType
import com.patres.automation.FileType
import com.patres.automation.gui.controller.model.*
import com.patres.automation.model.RootSchemaGroupModel
import com.patres.automation.validation.Validationable


interface ActionBootable {

    fun controllerType(): ActionBootControllerType
    fun bundleName(): String
    fun validation(): Validationable?
    fun createController(): (RootSchemaGroupModel) -> AutomationController<*>

    fun addController(): (RootSchemaGroupModel) -> Unit {
        return { root: RootSchemaGroupModel -> root.addActionBlocks(createController().invoke(root)) }
    }

}
