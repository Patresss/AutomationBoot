package com.patres.languagepopup.model

import com.patres.languagepopup.gui.controller.SchemaGroupController
import com.patres.languagepopup.util.LoaderFactory
import javafx.scene.Node

class SchemaGroup(rootParent: RootSchemaGroup, parent: SchemaGroup?) : AutomationModel(rootParent, parent) {

    val controller = LoaderFactory.loadSchemaGroup().getController<SchemaGroupController>()

    val actionBlocks = ArrayList<AutomationModel>()

    var schemaGroups = emptyList<SchemaGroup>()
        get() = actionBlocks.filterIsInstance<SchemaGroup>()

    var allChildrenActionBlocks = emptyList<AutomationModel>()
        get() = actionBlocks + schemaGroups.flatMap { it.allChildrenActionBlocks }

    init {
        controller.actionBlock = this
    }

    fun unselectSecectActionButton() {
        controller.unselectAction()
    }

    fun addNewSchemaGroup(name: String = "Group") {
        val newSchemaGroup = SchemaGroup(rootParent, this)
        newSchemaGroup.controller.groupNameTextField.text = name
        addActionBlocks(newSchemaGroup)
    }

    fun addActionBlocks(actionBlock: AutomationModel) {
        actionBlocks.add(actionBlock)
        actionBlock.parent = this
        controller.getMainInsideNode().children.add(actionBlock.getMainNode())
    }

    fun addActionBlockToList(actionBlock: AutomationModel, index: Int) {
        actionBlocks.add(actionBlock)
        actionBlock.parent = this
        controller.getMainInsideNode().children.add(index, actionBlock.getMainNode())

    }

    fun moveActionBlockAbove(actionBlock: AutomationModel) {
        actionBlock.parent?.removeNode(actionBlock)

        val index = parent?.controller?.getMainInsideNode()?.children?.indexOf(getMainNode())?: 0
        parent?.addActionBlockToList(actionBlock, index)
    }

    fun moveActionBlockUnder(actionBlock: AutomationModel) {
        actionBlock.parent?.removeNode(actionBlock)

        val index = (parent?.controller?.getMainInsideNode()?.children?.indexOf(getMainNode())?: 0) + 1
        parent?.addActionBlockToList(actionBlock, index)
    }


    fun addActionBlockToTop(actionBlock: AutomationModel) {
        actionBlock.parent?.removeNode(actionBlock)

        actionBlocks.add(actionBlock)
        actionBlock.parent = this
        controller.getMainInsideNode().children.add(0, actionBlock.getMainNode())
    }

    fun addActionBlockToBottom(actionBlock: AutomationModel) {
        actionBlock.parent?.removeNode(actionBlock)

        actionBlocks.add(actionBlock)
        actionBlock.parent = this
        controller.getMainInsideNode().children.add(actionBlock.getMainNode())
    }

    fun removeNode(actionBlock: AutomationModel) {
        actionBlocks.remove(actionBlock)
        controller.getMainInsideNode().children.remove(actionBlock.getMainNode())
    }

    override fun getMainNode(): Node = controller.getMainOutsideNode()

    override fun getMainInsideNode(): Node = controller.getMainInsideNode()

}