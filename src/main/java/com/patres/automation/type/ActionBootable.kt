package com.patres.automation.type

import com.patres.automation.gui.controller.box.AbstractBox
import com.patres.automation.gui.controller.model.RootSchemaGroupController
import com.patres.automation.validation.Validationable


interface ActionBootable {

    fun bundleName(): String
    fun validation(): Validationable?
    fun createNewActionBox(): () -> AbstractBox<*>

    fun addNewController(): (RootSchemaGroupController) -> Unit {
        return { controller: RootSchemaGroupController -> controller.addActionBlocks(createNewActionBox().invoke()) }
    }

}
