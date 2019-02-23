package com.patres.automation.gui.controller

import com.jfoenix.controls.JFXButton
import com.jfoenix.controls.JFXListCell
import com.jfoenix.controls.JFXListView
import com.jfoenix.controls.JFXPopup
import com.patres.automation.gui.controller.model.RootSchemaGroupController
import com.patres.automation.menuItem.MenuItem
import com.patres.automation.model.RootSchemaGroupModel
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView
import javafx.collections.FXCollections
import javafx.event.EventHandler
import javafx.scene.Node
import javafx.scene.control.ListView
import javafx.util.Callback


class ActionBarController(val rootSchemaGroupController: RootSchemaGroupController) {

    var actionBox = rootSchemaGroupController.actionBox

    val nodeActionMap = HashMap<Node, MenuItem>()
    val listViews = ArrayList<ListView<MenuItem>>()

    var rootModel: RootSchemaGroupModel? = null
        get() = rootSchemaGroupController.model

    init {
        addActions()
    }

    fun initAfterSetModel() {
        updateDisabledButtons()
        updateActions()
    }

    private fun addActions() {
//        val actionToAdd = listOf(
//                MenuItem.RUN,
//                MenuItem.MOVE_TO_UP,
//                MenuItem.MOVE_TO_DOWN,
//                MenuItem.REMOVE,
//
//                MenuItem.ADD_GROUP,
//
//                MenuItem.MOVE_MOUSE,
//
//                MenuItem.LEFT_MOUSE_BUTTON,
//                MenuItem.MIDDLE_MOUSE_BUTTON,
//                MenuItem.RIGHT_MOUSE_BUTTON)
        MenuItem.values()
                .filter { it.parent == null }
                .forEach { createGroup(it) }
    }

    private fun createGroup(action: MenuItem) {
        val button = createButton(action)
        val nestedAction = MenuItem.findAllWithAction(action)
        if (!nestedAction.isEmpty()) {
            createPopup(nestedAction, button)
        } else {
            nodeActionMap[button] = action
        }
        actionBox.children.add(button)

    }

    private fun createPopup(nestedAction: List<MenuItem>, button: JFXButton) {
        val actions = FXCollections.observableArrayList<MenuItem>(nestedAction)
        val listView = JFXListView<MenuItem>().apply { items = actions }

        listView.cellFactory = Callback {
            object : JFXListCell<MenuItem>() {
                override fun updateItem(item: MenuItem?, empty: Boolean) {
                    super.updateItem(item, empty)
                    if (item != null) {
                        text = item.actionName
                    }
                }
            }
        }
        val popup = JFXPopup(listView)
        listViews.add(listView)
        button.setOnMouseClicked { popup.show(button, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT, 35.0, 0.0) }
    }

    private fun createButton(action: MenuItem): JFXButton {
        return JFXButton().apply {
            styleClass.add("animated-option-button")
            graphic = FontAwesomeIconView(action.graphic).apply { styleClass.add("sub-icon") }
        }
    }

    fun updateDisabledButtons() {
        rootModel?.let { model ->
            nodeActionMap.forEach { (button, action) ->
                button.isDisable = action.shouldBeDisabled(model)
            }
        }
    }

    fun updateActions() {
        rootModel?.let { model ->
            nodeActionMap.forEach { (button, action) ->
                button.onMouseClicked = EventHandler { action.menuItemHandler(model) }
            }
            listViews.forEach { listView ->
                listView.onMouseClicked = EventHandler { listView.selectionModel.selectedItem.menuItemHandler(model) }
            }
        }
    }

}