package com.patres.automation.gui.controller.model

import com.jfoenix.controls.JFXComboBox
import com.patres.automation.action.AbstractAction
import com.patres.automation.mapper.TextAreaActionMapper
import com.patres.automation.mapper.model.TextAreaActionSerialized
import com.patres.automation.type.ActionBootComboBox
import com.patres.automation.type.ActionBootTextArea
import javafx.fxml.FXML
import javafx.scene.control.ComboBox

class ComboBoxController(
        action: ActionBootComboBox
) : AutomationController<ActionBootComboBox>("ComboBoxAction.fxml", action) {

    @FXML
    lateinit var comboBox: ComboBox<String>
}