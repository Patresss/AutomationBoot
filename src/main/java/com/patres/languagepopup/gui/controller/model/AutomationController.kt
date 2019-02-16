package com.patres.languagepopup.gui.controller.model

import com.jfoenix.controls.JFXButton
import com.patres.languagepopup.model.AutomationModel
import javafx.fxml.FXML


abstract class AutomationController {

    @FXML
    lateinit var selectActionButton: JFXButton

    abstract fun getModel(): AutomationModel<out AutomationController>

    @FXML
    fun selectAction() {
        getModel().root.unselectAllButton()
        selectActionButton.styleClass.add("select-action-button-selected")
        getModel().root.selectedModel = getModel()
    }

    fun unselectAction() {
        selectActionButton.styleClass.remove("select-action-button-selected")
    }

}