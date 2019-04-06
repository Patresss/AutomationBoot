package com.patres.automation.gui.controller.model

import com.patres.automation.action.TextActionModel

open class TextFieldActionController(
        model: TextActionModel<out TextActionController>? = null,
        fxmlFile: String = "TextFieldAction.fxml",
        labelText: String = ""
) : TextActionController(model, fxmlFile, labelText)