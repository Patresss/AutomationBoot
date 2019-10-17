package com.patres.automation.gui.controller.model

import com.patres.automation.action.AbstractAction
import com.patres.automation.mapper.TextAreaActionMapper
import com.patres.automation.mapper.model.TextAreaActionSerialized
import com.patres.automation.model.RootSchemaGroupModel
import com.patres.automation.type.ActionBootTextArea

class TextAreaActionController(
        root: RootSchemaGroupModel,
        parent: SchemaGroupController?,
        action: ActionBootTextArea
) : TextActionController<ActionBootTextArea>("AreaFieldAction.fxml", root, parent, action) {

    override fun toModel(): AbstractAction {
        return TextAreaActionMapper.controllerToModel(this)
    }

    override fun toSerialized(): TextAreaActionSerialized {
        return TextAreaActionMapper.controllerToSerialized(this)
    }

}