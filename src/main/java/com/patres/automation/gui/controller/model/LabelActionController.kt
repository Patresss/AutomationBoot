package com.patres.automation.gui.controller.model

import com.patres.automation.model.AutomationModel
import javafx.fxml.FXML
import javafx.scene.Node
import javafx.scene.control.Label
import javafx.scene.layout.Pane

open class LabelActionController(
        model: AutomationModel<out LabelActionController>? = null,
        fxmlFile: String = "LabelAction.fxml",
        private val labelText: String = ""
) : AutomationController(model, fxmlFile) {

    init {
        actionLabel.text = labelText
    }

    @FXML
    lateinit var actionLabel: Label

    fun getMainNode(): Node = this

    fun getMainInsideNode(): Pane = this

    override fun getNodesToSelect(): List<Node> = super.getNodesToSelect() + listOf(this)

}
