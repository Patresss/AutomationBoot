package com.patres.languagepopup.model

import com.patres.languagepopup.gui.controller.model.AutomationController
import com.patres.languagepopup.gui.controller.model.RootSchemaGroupController
import com.patres.languagepopup.util.LoaderFactory

class RootSchemaGroupModel(val controller: RootSchemaGroupController) {

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

    var schemaGroups = emptyList<SchemaGroupModel>()
        get() = actionBlocks.filterIsInstance<SchemaGroupModel>()


    fun initAfterSetController() {
        controller.actionBarController.initAfterSetModel()
    }

    fun addNewSchemaGroup(name: String = "Group") {
        schemaGroup.addNewSchemaGroup(name)
    }

    fun addActionBlocks(actionBlock: AutomationModel<out AutomationController>) {
        schemaGroup.addActionBlocks(actionBlock)
    }

    fun unselectAllButton() {
        allChildrenActionBlocks.forEach { it.unselectSelectActionButton() }
    }

    fun removeSelectedModel() {
        selectedModel?.let {
            it.parent?.removeNode(it)
        }
        selectedModel = null
    }

    fun addTextAction() {
        val textActionModel = LoaderFactory.createTextActionModel(this, schemaGroup)
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