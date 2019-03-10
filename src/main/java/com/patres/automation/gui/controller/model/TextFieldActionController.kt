package com.patres.automation.gui.controller.model

import com.patres.automation.action.TextFieldActionModel

open class TextFieldActionController(
        model: TextFieldActionModel<out TextFieldActionController>,
        fxmlFile: String = "TextFieldAction.fxml"
) : TextActionController(model, fxmlFile)