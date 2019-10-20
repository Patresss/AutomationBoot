package com.patres.automation.gui.controller.model

import com.patres.automation.action.AbstractAction
import com.patres.automation.mapper.TextFieldActionMapper
import com.patres.automation.mapper.model.TextFieldActionSerialized
import com.patres.automation.type.ActionBootTextField

class TextFieldActionController(
        action: ActionBootTextField,
        fxmlFile: String = "TextFieldAction.fxml"
) : TextActionController<ActionBootTextField>(fxmlFile, action) {

    override fun toModel(): AbstractAction? {
        action.validation()?.check(value)
        return TextFieldActionMapper.controllerToModel(this)
    }

    override fun toSerialized(): TextFieldActionSerialized {
        return TextFieldActionMapper.controllerToSerialized(this)
    }

}