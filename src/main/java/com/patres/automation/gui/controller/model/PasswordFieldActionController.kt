package com.patres.automation.gui.controller.model

import com.patres.automation.type.ActionBootPasswordField

class PasswordFieldActionController(
        action: ActionBootPasswordField,
        fxmlFile: String = "PasswordFieldAction.fxml"
) : TextActionController<ActionBootPasswordField>(fxmlFile, action)