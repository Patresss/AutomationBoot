package com.patres.automation.type

import com.patres.automation.action.RootSchemaGroupModel
import com.patres.automation.gui.controller.box.AbstractBox
import com.patres.automation.validation.Validationable


interface ActionBootable {

    fun bundleName(): String
    fun validation(): Validationable?
    fun createActinBox(): () -> AbstractBox<*>

    fun addController(): (RootSchemaGroupModel) -> Unit {
        return { root: RootSchemaGroupModel -> root.controller.addActionBlocks(createActinBox().invoke()) }
    }

}
