package com.patres.automation.gui.controller.model

import com.patres.automation.type.ActionBootComboBox
import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.scene.control.ComboBox

class ComboBoxController<OPTION_TYPE>(
        action: ActionBootComboBox,
        options: List<OPTION_TYPE>
) : AutomationController<ActionBootComboBox>("ComboBoxAction.fxml", action) {

    @FXML
    lateinit var comboBox: ComboBox<OPTION_TYPE>

    init {
        comboBox.items = FXCollections.observableList(options)
    }
}