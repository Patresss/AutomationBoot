package com.patres.automation.mapper

import com.patres.automation.action.AbstractAction
import com.patres.automation.gui.controller.model.AutomationController
import com.patres.automation.mapper.model.AutomationActionSerialized
import javafx.beans.property.BooleanProperty


interface Mapper<ControllerType : Any, ActionType : AbstractAction, SerializedType : Any> {

    fun controllerToModel(controller: ControllerType): ActionType?

    fun controllerToSerialized(controller: ControllerType): SerializedType

    fun serializedToController(serialized: SerializedType): ControllerType

    fun serializedToModel(serialized: SerializedType, automationRunningProperty: BooleanProperty?): ActionType

}