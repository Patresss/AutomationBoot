package com.patres.automation.gui.controller.model

import com.patres.automation.type.ActionBootTextField

class TextFieldActionController(
        action: ActionBootTextField,
        fxmlFile: String = "TextFieldAction.fxml"
) : TextActionController<ActionBootTextField>(fxmlFile, action)