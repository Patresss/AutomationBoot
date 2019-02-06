package com.patres.languagepopup.model

import com.patres.languagepopup.gui.controller.RootSchemaGroupController
import com.patres.languagepopup.util.LoaderFactory

class RootSchemaGroup {

    val controller = LoaderFactory.loadRootSchemaGroup().getController<RootSchemaGroupController>()

    val schemaGroup = SchemaGroup(this, null)

    var actionBlocks = ArrayList<AutomationModel>()
        get() = schemaGroup.actionBlocks

    var allChildrenActionBlocks = emptyList<AutomationModel>()
        get() = schemaGroup.allChildrenActionBlocks

    var allSchemaActionBlocks = emptyList<SchemaGroup>()
        get() = allChildrenActionBlocks.filterIsInstance<SchemaGroup>()

    var schemaGroups = emptyList<SchemaGroup>()
        get() = actionBlocks.filterIsInstance<SchemaGroup>()



    fun addNewSchemaGroup(name: String = "Group") {
        schemaGroup.addNewSchemaGroup(name)
    }

    fun addActionBlocks(actionBlock: AutomationModel) {
        schemaGroup.addActionBlocks(actionBlock)
    }

    fun unselectAllButton() {
        allSchemaActionBlocks.forEach { it.unselectSecectActionButton() }
    }

}