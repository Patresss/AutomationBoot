package com.patres.automation.gui.controller.model

import com.patres.automation.Main
import com.patres.automation.gui.custom.AutomationBootableFactoryCell
import com.patres.automation.gui.menuItem.MenuItem
import com.patres.automation.type.ActionBootable
import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.scene.Node
import javafx.scene.control.ComboBox
import javafx.scene.control.Label
import javafx.scene.layout.GridPane
import javafx.scene.layout.Pane


abstract class LabelActionController<ActionBootType : ActionBootable>(
        fxmlFile: String,
        action: ActionBootType
) : AutomationController<ActionBootType>(fxmlFile, action) {

    private var actionComboBox: ComboBox<MenuItem>? = null

    init {
        actionLabel.textProperty().bind(Main.createStringBinding(action.bundleName()))
    }

    @FXML
    override fun initialize() {
        initSelectedAction()
        super.initialize()
    }

    private fun initSelectedAction() {
        val actions = MenuItem.values().filter { action.javaClass.isInstance(it.actionBoot) }
        val observableListOfActions = FXCollections.observableList(actions)
        val selected = actions.firstOrNull { it.actionBoot == action }
        if (observableListOfActions.size > 1 && selected != null) {
            actionLabel.isVisible = false
            val rowIndex = GridPane.getRowIndex(actionLabel) ?: 0
            val columnIndex = GridPane.getColumnIndex(actionLabel) ?: 0
            actionComboBox = ComboBox(observableListOfActions).apply {
                maxWidth = Double.MAX_VALUE
                val factory = AutomationBootableFactoryCell()
                setCellFactory { factory.createCell() }
                buttonCell = factory.createButtonCell()
                selectionModel.select(selected)
                valueProperty().addListener { _, _, newValue -> action = newValue.actionBoot as ActionBootType }
                toBack()
            }
            gridPane.add(actionComboBox, columnIndex, rowIndex)
        }
    }

    @FXML
    lateinit var actionLabel: Label

    fun getMainNode(): Node = this

    fun getMainInsideNode(): Pane = this

    override fun getNodesToSelect(): List<Node> = super.getNodesToSelect() + listOfNotNull(this, actionComboBox)

}
