package com.patres.automation.gui.controller.box

import com.patres.automation.action.AbstractAction
import com.patres.automation.gui.controller.model.*
import com.patres.automation.mapper.*
import com.patres.automation.mapper.model.AutomationActionSerialized
import com.patres.automation.type.ActionBootable
import com.patres.automation.util.extension.getAllNodes
import javafx.beans.property.BooleanProperty
import javafx.scene.input.MouseEvent


class ActionBox<ActionBootType : ActionBootable>(
        val controller: AutomationController<ActionBootType>
) : AbstractBox<ActionBootType>("ActionBox.fxml") {

    init {
        actionPlaceholder.children.add(controller)
        rootPane.getAllNodes().forEach { node -> node.addEventHandler(MouseEvent.MOUSE_PRESSED) { selectAction() } }
    }

    override fun addNewAction(abstractBox: AbstractBox<*>) {
        val index = schemaGroupParent?.abstractBlocks?.indexOf(this) ?: 0
        schemaGroupParent?.addActionBlockToList(abstractBox, index + 1)
    }

    override fun toModel(automationRunningProperty: BooleanProperty): AbstractAction {
        controller.checkValidation()
        return when (controller) {
            is BrowseFileActionController -> BrowserActionMapper.controllerToModel(controller, automationRunningProperty)
            is KeyboardButtonActionController -> KeyboardFieldActionMapper.controllerToModel(controller, automationRunningProperty)
            is MousePointActionController -> MousePointActionMapper.controllerToModel(controller, automationRunningProperty)
            is TextAreaActionController -> TextAreaActionMapper.controllerToModel(controller, automationRunningProperty)
            is TextFieldActionController -> TextFieldActionMapper.controllerToModel(controller, automationRunningProperty)
            is TimeActionController -> TimeActionMapper.controllerToModel(controller, automationRunningProperty)
            else -> throw IllegalStateException("Controller ${controller.javaClass.name} toModel is not correct") // If sealed class will be allowed it should be replaced
        }
    }

    override fun toSerialized(): AutomationActionSerialized {
        return when (controller) {
            is BrowseFileActionController -> BrowserActionMapper.controllerToSerialized(controller)
            is KeyboardButtonActionController -> KeyboardFieldActionMapper.controllerToSerialized(controller)
            is MousePointActionController -> MousePointActionMapper.controllerToSerialized(controller)
            is TextAreaActionController -> TextAreaActionMapper.controllerToSerialized(controller)
            is TextFieldActionController -> TextFieldActionMapper.controllerToSerialized(controller)
            is TimeActionController -> TimeActionMapper.controllerToSerialized(controller)
            else -> throw IllegalStateException("Controller ${controller.javaClass.name} toSerialized is not correct") // If sealed class will be allowed it should be replaced
        }
    }

}


