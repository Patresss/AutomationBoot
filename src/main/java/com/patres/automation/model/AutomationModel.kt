package com.patres.automation.model

import com.patres.automation.gui.controller.model.AutomationController
import javafx.scene.Node

abstract class AutomationModel<ControllerType : AutomationController>(
        val root: RootSchemaGroupModel,
        var parent: SchemaGroupModel?
) {

    abstract val controller: ControllerType

    val robot = root.robot


    fun unselectSelectActionButton() {
        controller.unselectAction()
    }

    abstract fun getMainNode(): Node

    abstract fun runAction()

    abstract fun getMainInsideNode(): Node


    open fun checkValidations() {
    }

    fun isLast(): Boolean = parent?.actionBlocks?.last() == this

    fun isFirst(): Boolean = parent?.actionBlocks?.first() == this

    fun swap(actionBlockToSwap: AutomationModel<out AutomationController>) {
        parent?.swap(this, actionBlockToSwap)
    }

    fun addActionBlockUnder(actionBlock: AutomationModel<out AutomationController>) {
        val index = parent?.actionBlocks?.indexOf(this)?: 0
        parent?.addActionBlockToList(actionBlock, index + 1)
    }

    fun downActionBlock() {
        val bottomNode = findNodeOnTheBottomFromGroup()
        when {
            bottomNode == null && root.schemaGroup != parent -> parent?.leaveGroupToDown(this)
            bottomNode is ActionNodeModel -> swap(bottomNode)
            bottomNode is SchemaGroupModel -> bottomNode.moveActionBlockToTop(this)
        }
    }

    fun upActionBlock() {
        val topNode = findNodeOnTheTopFromGroup()
        when {
            topNode == null && root.schemaGroup != parent -> parent?.leaveGroupToUp(this)
            topNode is ActionNodeModel -> swap(topNode)
            topNode is SchemaGroupModel -> topNode.moveActionBlockToBottom(this)
        }
    }

    fun findNodeOnTheTop(): AutomationModel<out AutomationController>? {
        val topNodeInGroup = findNodeOnTheTopFromGroup()
        if (topNodeInGroup != null) {
            return topNodeInGroup
        }
        return parent
    }


    private fun findNodeOnTheBottomFromGroup(): AutomationModel<out AutomationController>? {
        val parentVal = parent
        return if (!isLast() && parentVal != null) {
            val currentActionIndex = parentVal.actionBlocks.indexOf(this)
            parentVal.actionBlocks[currentActionIndex + 1]
        } else {
            null
        }
    }

    private fun findNodeOnTheTopFromGroup(): AutomationModel<out AutomationController>? {
        val parentVal = parent
        return if (!isFirst() && parentVal != null) {
            val currentActionIndex = parentVal.actionBlocks.indexOf(this)
            parentVal.actionBlocks[currentActionIndex - 1]
        } else {
            null
        }
    }

}