package com.patres.automation.model

import com.patres.automation.Main
import com.patres.automation.excpetion.ApplicationException
import com.patres.automation.gui.controller.model.AutomationController
import com.patres.automation.gui.controller.model.RootSchemaGroupController
import com.patres.automation.gui.dialog.ExceptionHandlerDialog
import com.patres.automation.keyboard.GlobalKeyListener
import com.sun.glass.ui.Robot
import org.slf4j.LoggerFactory

class RootSchemaGroupModel {

    companion object {
        private val LOGGER = LoggerFactory.getLogger(RootSchemaGroupModel::class.java)
    }

    val controller: RootSchemaGroupController = RootSchemaGroupController(this)

    var robot: Robot = com.sun.glass.ui.Application.GetApplication().createRobot()

    var schemaGroup = SchemaGroupModel(this, null)
        set(value) {
            field = value
            loadControllerContent()
        }

    var selectedModel: AutomationModel<out AutomationController>? = null
        set(value) {
            field = value
            controller.actionBarController.updateDisabledButtons()
        }

    private val allChildrenActionBlocks
        get() = schemaGroup.allChildrenActionBlocks

    private val allChildrenActionBlocksRoot
        get() = allChildrenActionBlocks + schemaGroup

    init {
        controller.actionBarController.initAfterSetModel()
        loadControllerContent()
    }



    fun unselectAllButton() {
        allChildrenActionBlocksRoot.forEach { it.unselectSelectActionButton() }
    }

    fun removeSelectedModel() {
        val futureSelectedNode = selectedModel?.findNodeOnTheTop()
        selectedModel?.let {
            it.parent?.removeNode(it)
            futureSelectedNode?.controller?.selectAction()
        }
    }

    fun addNodeAction(textActionModel: ActionNodeModel<out AutomationController>) {
        addNewActionModel(textActionModel)
    }

    fun addSchemaGroup() {
        val schemaGroupModel = SchemaGroupModel(this, schemaGroup)
        addNewActionModel(schemaGroupModel)
    }

    fun runAutomation() {
        try {
            GlobalKeyListener.isStop = false
            schemaGroup.checkValidation()
            Main.mainStage.isIconified = true
            schemaGroup.runAction()
            Thread.sleep(200)
        } catch (e: ApplicationException) {
            LOGGER.error("ApplicationException: {}", e.message)
            val dialog = ExceptionHandlerDialog(e)
            dialog.show()
        } catch (e: Exception) {
            LOGGER.error("Exception: {}", e)
        } finally {
            Main.mainStage.isIconified = false
        }
    }


    fun getSelectedSchemaGroupModel(): SchemaGroupModel {
        val selectedModelVal = selectedModel
        return if (selectedModelVal != null && selectedModelVal is SchemaGroupModel) {
            selectedModelVal
        } else {
            schemaGroup
        }
    }

    private fun addActionBlocks(actionBlock: AutomationModel<out AutomationController>) {
        schemaGroup.addActionBlocks(actionBlock)
    }

    private fun addNewActionModel(actionModel: AutomationModel<out AutomationController>) {
        val selectedModelVal = selectedModel
        when (selectedModelVal) {
            null -> addActionBlocks(actionModel)
            is SchemaGroupModel -> selectedModelVal.moveActionBlockToBottom(actionModel)
            is ActionNodeModel -> selectedModelVal.addActionBlockUnder(actionModel)
        }
        actionModel.controller.selectAction()
    }

    private fun loadControllerContent() {
        controller.insidePane.content = schemaGroup.controller
        schemaGroup.controller.minHeightProperty().bind(controller.heightProperty())
    }

}