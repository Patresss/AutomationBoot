package com.patres.automation.mapper

import com.patres.automation.action.AbstractAction
import com.patres.automation.parameter.sent.SentParameter
import javafx.beans.property.BooleanProperty


interface Mapper<ControllerType : Any, ActionType : AbstractAction, SerializedType : Any> {

    fun controllerToModel(controller: ControllerType, automationRunningProperty: BooleanProperty, parameters: Set<SentParameter>): ActionType?

    fun controllerToSerialized(controller: ControllerType): SerializedType

    fun serializedToController(serialized: SerializedType): ControllerType

    fun serializedToModel(serialized: SerializedType, automationRunningProperty: BooleanProperty, parameters: Set<SentParameter>): ActionType

}