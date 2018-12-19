package com.patres.languagepopup.gui

class RootSchemaGroup {

     val schemaGroup = SchemaGroup(this, null)

    var actionBlocks = ArrayList<ActionBlock>()
        get() = schemaGroup.actionBlocks

    var allChildrenActionBlocks = emptyList<ActionBlock>()
        get() = schemaGroup.allChildrenActionBlocks

    var schemaGroups = emptyList<SchemaGroup>()
        get() = actionBlocks.filterIsInstance<SchemaGroup>()


    fun addNewSchemaGroup(name: String = "Group") {
        schemaGroup.addNewSchemaGroup(name)
    }

    fun addActionBlocks(actionBlock: ActionBlock) {
        schemaGroup.addActionBlocks(actionBlock)
    }

}