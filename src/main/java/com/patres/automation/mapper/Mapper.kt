package com.patres.automation.mapper

import com.patres.automation.action.AbstractAction
import com.patres.automation.gui.controller.model.AutomationController
import com.patres.automation.gui.controller.model.SchemaGroupController
import com.patres.automation.mapper.model.AutomationActionSerialized
import com.patres.automation.model.RootSchemaGroupModel


interface Mapper<ControllerType : AutomationController<*>, ActionType : AbstractAction, SerializedType : AutomationActionSerialized> {

    fun controllerToModel(controller: ControllerType): ActionType

    fun controllerToSerialized(controller: ControllerType): SerializedType

    fun serializedToController(serialized: SerializedType): ControllerType

}