package com.patres.languagepopup.gui

import com.patres.languagepopup.gui.controller.SchemaGroupController
import javafx.scene.Node

class SchemaGroup(rootParent: RootSchemaGroup, parent: SchemaGroup?) : ActionBlock(rootParent, parent) {

    val controller = LoaderFactory.loadSchemaGroup().getController<SchemaGroupController>()

    val actionBlocks = ArrayList<ActionBlock>()

    var schemaGroups = emptyList<SchemaGroup>()
        get() = actionBlocks.filterIsInstance<SchemaGroup>()

    var allChildrenActionBlocks = emptyList<ActionBlock>()
        get() = actionBlocks + schemaGroups.flatMap { it.allChildrenActionBlocks }

    init {
        controller.actionBlock = this
    }

    fun addNewSchemaGroup(name: String = "Group") {
        val newSchemaGroup = SchemaGroup(rootParent, this)
        newSchemaGroup.controller.groupNameTextField.text = name
        addActionBlocks(newSchemaGroup)
    }

    fun addActionBlocks(actionBlock: ActionBlock) {
        actionBlocks.add(actionBlock)
        actionBlock.parent = this
        controller.getMainInsideNode().children.add(actionBlock.getMainNode())
    }

    fun addActionBlockToList(actionBlock: ActionBlock, index: Int) {
        actionBlocks.add(actionBlock)
        actionBlock.parent = this
        controller.getMainInsideNode().children.add(index, actionBlock.getMainNode())

    }

    fun moveActionBlockAbove(actionBlock: ActionBlock) {
        actionBlock.parent?.removeNode(actionBlock)

        val index = parent?.controller?.getMainInsideNode()?.children?.indexOf(getMainNode())?: 0
        parent?.addActionBlockToList(actionBlock, index)
    }

    fun moveActionBlockUnder(actionBlock: ActionBlock) {
        actionBlock.parent?.removeNode(actionBlock)

        val index = (parent?.controller?.getMainInsideNode()?.children?.indexOf(getMainNode())?: 0) + 1
        parent?.addActionBlockToList(actionBlock, index)
    }


    fun addActionBlockToTop(actionBlock: ActionBlock) {
        actionBlock.parent?.removeNode(actionBlock)

        actionBlocks.add(actionBlock)
        actionBlock.parent = this
        controller.getMainInsideNode().children.add(0, actionBlock.getMainNode())
    }

    fun addActionBlockToBottom(actionBlock: ActionBlock) {
        actionBlock.parent?.removeNode(actionBlock)

        actionBlocks.add(actionBlock)
        actionBlock.parent = this
        controller.getMainInsideNode().children.add(actionBlock.getMainNode())
    }

    fun removeNode(actionBlock: ActionBlock) {
        actionBlocks.remove(actionBlock)
        controller.getMainInsideNode().children.remove(actionBlock.getMainNode())
    }

    override fun getMainNode(): Node = controller.getMainOutsideNode()

    override fun getMainInsideNode(): Node = controller.getMainInsideNode()

}