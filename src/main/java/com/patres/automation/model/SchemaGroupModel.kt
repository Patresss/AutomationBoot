package com.patres.automation.model

import com.patres.automation.gui.controller.model.AutomationController
import com.patres.automation.gui.controller.model.SchemaGroupController
import com.patres.automation.util.swap
import java.util.*

class SchemaGroupModel(root: RootSchemaGroupModel, parent: SchemaGroupModel?) : AutomationModel<SchemaGroupController>(root, parent) {

    override val controller: SchemaGroupController = SchemaGroupController(this)

    val actionBlocks = ArrayList<AutomationModel<out AutomationController>>()

    private val schemaGroups
        get() = actionBlocks.filterIsInstance<SchemaGroupModel>()

    val allChildrenActionBlocks: List<AutomationModel<out AutomationController>>
        get() = actionBlocks + schemaGroups.flatMap { it.allChildrenActionBlocks }

    init {
        controller.afterInit()
    }

    fun addActionBlocks(actionBlock: AutomationModel<out AutomationController>) {
        actionBlocks.add(actionBlock)
        actionBlock.parent = this
        controller.getMainInsideNode().children.add(actionBlock.controller)
    }

    fun addActionBlockToList(actionBlock: AutomationModel<out AutomationController>, index: Int) {
        actionBlocks.add(index, actionBlock)
        actionBlock.parent = this
        controller.getMainInsideNode().children.add(index, actionBlock.controller)
    }

    fun leaveGroupToUp(actionBlock: AutomationModel<out AutomationController>) {
        actionBlock.parent?.removeNode(actionBlock)
        val index = parent?.controller?.getMainInsideNode()?.children?.indexOf(controller) ?: 0
        parent?.addActionBlockToList(actionBlock, index)
    }

    fun leaveGroupToDown(actionBlock: AutomationModel<out AutomationController>) {
        actionBlock.parent?.removeNode(actionBlock)
        val index = (parent?.controller?.getMainInsideNode()?.children?.indexOf(controller) ?: 0) + 1
        parent?.addActionBlockToList(actionBlock, index)
    }

    fun moveActionBlockToTop(actionBlock: AutomationModel<out AutomationController>) {
        actionBlock.parent?.removeNode(actionBlock)
        actionBlocks.add(0, actionBlock)
        actionBlock.parent = this
        controller.getMainInsideNode().children.add(0, actionBlock.controller)
    }

    fun moveActionBlockToBottom(actionBlock: AutomationModel<out AutomationController>) {
        actionBlock.parent?.removeNode(actionBlock)
        actionBlocks.add(actionBlock)
        actionBlock.parent = this
        controller.getMainInsideNode().children.add(actionBlock.controller)
    }

    fun removeNode(actionBlock: AutomationModel<out AutomationController>) {
        actionBlocks.remove(actionBlock)
        controller.getMainInsideNode().children.remove(actionBlock.controller)
    }

    fun swap(actionBlock: AutomationModel<out AutomationController>, actionBlockToSwap: AutomationModel<out AutomationController>) {
        controller.getMainInsideNode().swap(actionBlock.controller, actionBlockToSwap.controller)
        actionBlocks.swap(actionBlock, actionBlockToSwap)
    }

    fun checkValidation() {
        allChildrenActionBlocks.forEach { it.checkValidations() }
    }

    fun getNumberOfIteration(): Int {
        val text = controller.iterationsTextField.text
        return if (text == "INF") {
            Integer.MAX_VALUE
        } else {
            try {
                Integer.parseInt(controller.iterationsTextField.text)
            } catch (nfe: NumberFormatException) {
                1
            }
        }
    }

    override fun runAction() {
        for (i in 0 until getNumberOfIteration()) {
            for (action in actionBlocks) {
                if (root.automationRunningProperty.get()) {
                    action.runAction()
                } else {
                    return
                }
            }
        }
    }

    fun getGroupName(): String = controller.groupNameTextField.text

    fun setGroupName(text: String) {
        controller.groupNameTextField.text = text
    }

    fun setNumberOfIterations(text: String) {
        controller.iterationsTextField.text = text
    }

}