package com.patres.automation.gui.controller.model

import com.patres.automation.action.TextActionModel

open class TextFieldActionController(
        model: TextActionModel<out TextActionController>,
        fxmlFile: String = "TextFieldAction.fxml"
) : TextActionController(model, fxmlFile)