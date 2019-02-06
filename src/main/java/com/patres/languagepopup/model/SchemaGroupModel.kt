package com.patres.languagepopup.model

import com.patres.languagepopup.gui.controller.model.AutomationController
import com.patres.languagepopup.gui.controller.model.SchemaGroupController
import com.patres.languagepopup.util.LoaderFactory
import javafx.scene.Node

class SchemaGroupModel(controller: SchemaGroupController, root: RootSchemaGroupModel, parent: SchemaGroupModel?) : AutomationModel<SchemaGroupController>(controller, root, parent) {

    val actionBlocks = ArrayList<AutomationModel<out AutomationController>>()

    var schemaGroups = emptyList<SchemaGroupModel>()
        get() = actionBlocks.filterIsInstance<SchemaGroupModel>()

    var allChildrenActionBlocks = emptyList<AutomationModel<out AutomationController>>()
        get() = actionBlocks + schemaGroups.flatMap { it.allChildrenActionBlocks }

    fun unselectSecectActionButton() {
        controller.unselectAction()
    }

    fun addNewSchemaGroup(name: String = "Group") {
        val newSchemaGroup = LoaderFactory.createSchemaGroupModel(root, this)
        newSchemaGroup.controller.groupNameTextField.text = name
        addActionBlocks(newSchemaGroup)
    }

    fun addActionBlocks(actionBlock: AutomationModel<out AutomationController>) {
        actionBlocks.add(actionBlock)
        actionBlock.parent = this
        controller.getMainInsideNode().children.add(actionBlock.getMainNode())
    }

    fun addActionBlockToList(actionBlock: AutomationModel<AutomationController>, index: Int) {
        actionBlocks.add(actionBlock)
        actionBlock.parent = this
        controller.getMainInsideNode().children.add(index, actionBlock.getMainNode())

    }

    fun moveActionBlockAbove(actionBlock: AutomationModel<AutomationController>) {
        actionBlock.parent?.removeNode(actionBlock)

        val index = parent?.controller?.getMainInsideNode()?.children?.indexOf(getMainNode())?: 0
        parent?.addActionBlockToList(actionBlock, index)
    }

    fun moveActionBlockUnder(actionBlock: AutomationModel<AutomationController>) {
        actionBlock.parent?.removeNode(actionBlock)

        val index = (parent?.controller?.getMainInsideNode()?.children?.indexOf(getMainNode())?: 0) + 1
        parent?.addActionBlockToList(actionBlock, index)
    }


    fun addActionBlockToTop(actionBlock: AutomationModel<AutomationController>) {
        actionBlock.parent?.removeNode(actionBlock)

        actionBlocks.add(actionBlock)
        actionBlock.parent = this
        controller.getMainInsideNode().children.add(0, actionBlock.getMainNode())
    }

    fun addActionBlockToBottom(actionBlock: AutomationModel<AutomationController>) {
        actionBlock.parent?.removeNode(actionBlock)

        actionBlocks.add(actionBlock)
        actionBlock.parent = this
        controller.getMainInsideNode().children.add(actionBlock.getMainNode())
    }

    fun removeNode(actionBlock: AutomationModel<AutomationController>) {
        actionBlocks.remove(actionBlock)
        controller.getMainInsideNode().children.remove(actionBlock.getMainNode())
    }

    override fun getMainNode(): Node = controller.getMainOutsideNode()

    override fun getMainInsideNode(): Node = controller.getMainInsideNode()

}