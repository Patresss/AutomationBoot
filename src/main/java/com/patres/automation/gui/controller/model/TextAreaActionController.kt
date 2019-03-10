package com.patres.automation.gui.controller.model

import com.patres.automation.model.AutomationModel

open class TextAreaActionController(
        model: AutomationModel<out TextActionController>,
        fxmlFile: String = "AreaFieldAction.fxml"
) : TextActionController(model, fxmlFile)