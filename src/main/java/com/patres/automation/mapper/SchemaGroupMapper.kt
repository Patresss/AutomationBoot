package com.patres.automation.mapper

import com.patres.automation.action.AbstractAction
import com.patres.automation.action.SchemaGroupModel
import com.patres.automation.gui.controller.box.SchemaGroupController
import com.patres.automation.mapper.model.AutomationActionSerialized
import com.patres.automation.mapper.model.SchemaGroupSerialized
import javafx.beans.property.BooleanProperty


object SchemaGroupMapper : Mapper<SchemaGroupController, SchemaGroupModel, SchemaGroupSerialized> {

    override fun controllerToModel(controller: SchemaGroupController): SchemaGroupModel {
        val actionBlockModels: List<AbstractAction> = controller.abstractBlocks.mapNotNull { it.toModel() }
        val iteration = controller.getNumberOfIteration()
        val automationRunningProperty = controller.root.automationRunningProperty
        return SchemaGroupModel(actionBlockModels, iteration, automationRunningProperty)
    }

    override fun controllerToSerialized(controller: SchemaGroupController): SchemaGroupSerialized {
        return controller.run {
            val serializedModels: List<AutomationActionSerialized> = abstractBlocks.map { it.toSerialized() }
            SchemaGroupSerialized(serializedModels, groupNameTextField.text, iterationsTextField.text)
        }
    }

    override fun serializedToController(serialized: SchemaGroupSerialized): SchemaGroupController {
        return SchemaGroupController().apply {
            groupNameTextField.text = serialized.groupName
            iterationsTextField.text = serialized.numberOfIterations
            serialized.actionList
                    .map { it.serializedToController() }
                    .forEach { this.addNewAction(it) }
        }
    }

    override fun serializedToModel(serialized: SchemaGroupSerialized, automationRunningProperty: BooleanProperty): SchemaGroupModel {
        val serializedModels: List<AbstractAction> = serialized.actionList.map { it.serializedToModel(automationRunningProperty) }
        return SchemaGroupModel(serializedModels, serialized.numberOfIterations.toInt(), automationRunningProperty)
    }

}