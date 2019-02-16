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

    fun addTextAction() {
        val selectedModelVal = selectedModel
        val textActionModel = LoaderFactory.createTextActionModel(this, schemaGroup)
        when (selectedModelVal) {
            null -> addActionBlocks(textActionModel)
            is SchemaGroupModel -> selectedModelVal.moveActionBlockToBottom(textActionModel)
            is TextActionModel -> selectedModelVal.addActionBlockUnder(textActionModel)
        }
    }

    fun removeSelectedModel() {
        selectedModel?.let {
            it.parent?.removeNode(it)
        }
        selectedModel = null
    }


}