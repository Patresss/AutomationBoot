package com.patres.languagepopup.model

import com.patres.languagepopup.action.Action
import com.patres.languagepopup.gui.controller.model.AutomationController
import com.patres.languagepopup.gui.controller.model.RootSchemaGroupController
import com.patres.languagepopup.util.LoaderFactory

class RootSchemaGroupModel(val controller: RootSchemaGroupController) {

    var robot = com.sun.glass.ui.Application.GetApplication().createRobot()

    val schemaGroup = LoaderFactory.createSchemaGroupModel(this, null)

    var selectedModel: AutomationModel<out AutomationController>? = null
        set(value) {
            field = value
            controller.actionBarController.updateDisabledButtons()
        }

    var actionBlocks = ArrayList<AutomationModel<out AutomationController>>()
        get() = schemaGroup.actionBlocks

    var allChildrenActionBlocks = emptyList<AutomationModel<out AutomationController>>()
        get() = schemaGroup.allChildrenActionBlocks

    var allChildrenActionBlocksRoot = emptyList<AutomationModel<out AutomationController>>()
        get() = allChildrenActionBlocks + schemaGroup

    var schemaGroups = emptyList<SchemaGroupModel>()
        get() = actionBlocks.filterIsInstance<SchemaGroupModel>()


    fun initAfterSetController() {
        controller.actionBarController.initAfterSetModel()
        schemaGroup.controller.mainSchemaBox.minHeightProperty().bind(controller.rootStackPane.heightProperty())
    }

    fun addNewSchemaGroup(name: String = "Group") {
        schemaGroup.addNewSchemaGroup(name)
    }

    fun addActionBlocks(actionBlock: AutomationModel<out AutomationController>) {
        schemaGroup.addActionBlocks(actionBlock)
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

    fun addTextAction(action: Action) {
        val textActionModel = LoaderFactory.createTextActionModel(this, schemaGroup, action)
        addNewActionModel(textActionModel)
    }

    fun addSchemaGroup() {
        val schemaGroupModel = LoaderFactory.createSchemaGroupModel(this, schemaGroup)
        addNewActionModel(schemaGroupModel)

    }

    private fun addNewActionModel(actionModel: AutomationModel<out AutomationController>) {
        val selectedModelVal = selectedModel
        when (selectedModelVal) {
            null -> addActionBlocks(actionModel)
            is SchemaGroupModel -> selectedModelVal.moveActionBlockToBottom(actionModel)
            is TextActionModel -> selectedModelVal.addActionBlockUnder(actionModel)
        }
        actionModel.controller.selectAction()
    }


}