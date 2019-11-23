package com.patres.automation.gui.controller.model

import com.patres.automation.Main
import com.patres.automation.action.RootSchemaGroupModel
import com.patres.automation.gui.custom.AutomationBootableFactoryCell
import com.patres.automation.gui.menuItem.MenuItem
import com.patres.automation.type.ActionBootable
import com.patres.automation.util.calculateTypedParent
import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Node
import javafx.scene.control.ComboBox
import javafx.scene.control.Label
import javafx.scene.layout.GridPane
import javafx.scene.layout.Pane
import javafx.scene.layout.StackPane


abstract class AutomationController<ActionBootType : ActionBootable>(
        fxmlFile: String,
        action: ActionBootType
) : StackPane() {

    @FXML
    lateinit var actionLabel: Label

    var action: ActionBootType = action
        set(value) {
            field = value
            checkUiValidation()
        }

    open fun checkValidation() {}
    open fun checkUiValidation() {}
    open fun shouldCheckUiValidation() = true

    val root: RootSchemaGroupModel?
        get() = calculateTypedParent(RootSchemaGroupController::class)?.model

    private var actionComboBox: ComboBox<MenuItem>? = null

    init {

        val fxmlLoader = FXMLLoader(javaClass.getResource("/fxml/$fxmlFile"))
        fxmlLoader.setRoot(this)
        fxmlLoader.setController(this)
        fxmlLoader.resources = Main.getBundle()
        fxmlLoader.load<AutomationController<*>>()

        actionLabel.textProperty().bind(Main.createStringBinding(action.bundleName()))

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
    lateinit var gridPane: GridPane

    @FXML
    open fun initialize() {
        initSelectedAction()
    }

    fun getMainNode(): Node = this

    fun getMainInsideNode(): Pane = this

}

