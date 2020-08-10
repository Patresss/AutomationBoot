package com.patres.automation.mapper

import com.patres.automation.action.AbstractAction
import com.patres.automation.action.SchemaGroupModel
import com.patres.automation.gui.controller.box.SchemaGroupController
import com.patres.automation.mapper.model.AutomationActionSerialized
import com.patres.automation.mapper.model.SchemaGroupSerialized
import com.patres.automation.parameter.sent.SentParameter
import javafx.beans.property.BooleanProperty
import org.slf4j.LoggerFactory


object SchemaGroupMapper : Mapper<SchemaGroupController, SchemaGroupModel, SchemaGroupSerialized> {

    private val logger = LoggerFactory.getLogger(SchemaGroupMapper::class.java)

    override fun controllerToModel(controller: SchemaGroupController, automationRunningProperty: BooleanProperty, parameters: Set<SentParameter>): SchemaGroupModel {
        logger.debug("SchemaGroup Mapping: Controller to Model")
        val actionBlockModels: List<AbstractAction> = if (controller.turnOnToggleButton.isSelected) {
            controller.abstractBlocks.mapNotNull { it.toModel(automationRunningProperty, parameters) }
        } else {
            emptyList()
        }
        return SchemaGroupModel(actionBlockModels, controller.getNumberOfIteration(), automationRunningProperty, controller)
    }

    override fun controllerToSerialized(controller: SchemaGroupController): SchemaGroupSerialized {
        logger.debug("SchemaGroup Mapping: Controller to Serialized")
        return controller.run {
            val serializedModels: List<AutomationActionSerialized> = abstractBlocks.map { it.toSerialized() }
            SchemaGroupSerialized(serializedModels, groupNameTextField.text, iterationsTextField.text, turnOnToggleButton.isSelected)
        }
    }

    override fun serializedToController(serialized: SchemaGroupSerialized): SchemaGroupController {
        logger.debug("SchemaGroup Mapping: Serialized to Controller")
        return SchemaGroupController().apply {
            groupNameTextField.text = serialized.groupName
            iterationsTextField.text = serialized.numberOfIterations
            turnOnToggleButton.isSelected = serialized.turnOn
            serialized.actionList
                    .map { it.serializedToController() }
                    .forEach { this.addNewAction(it) }
        }
    }

    override fun serializedToModel(serialized: SchemaGroupSerialized, automationRunningProperty: BooleanProperty, parameters: Set<SentParameter>): SchemaGroupModel {
        logger.debug("SchemaGroup Mapping: Serialized to Model")
        val serializedModels: List<AbstractAction> = if (serialized.turnOn) {
            serialized.actionList.map { it.serializedToModel(automationRunningProperty, parameters) }
        } else {
            emptyList()
        }
        return SchemaGroupModel(serializedModels, serialized.numberOfIterations.toInt(), automationRunningProperty, null)
    }

}