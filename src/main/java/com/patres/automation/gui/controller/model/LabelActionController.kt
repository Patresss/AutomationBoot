package com.patres.automation.gui.controller.model

import com.patres.automation.type.ActionBootable
import com.patres.automation.Main
import com.patres.automation.model.RootSchemaGroupModel
import javafx.fxml.FXML
import javafx.scene.Node
import javafx.scene.control.Label
import javafx.scene.layout.Pane

abstract class LabelActionController<ActionBootType: ActionBootable>(
        fxmlFile: String,
        action: ActionBootType
) : AutomationController<ActionBootType>(fxmlFile, action) {

    init {
        actionLabel.textProperty().bind(Main.createStringBinding(action.bundleName()))
    }

    @FXML
    lateinit var actionLabel: Label

    fun getMainNode(): Node = this

    fun getMainInsideNode(): Pane = this

    override fun getNodesToSelect(): List<Node> = super.getNodesToSelect() + listOf(this)

}
