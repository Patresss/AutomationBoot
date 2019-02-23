package com.patres.automation.gui.controller

import com.jfoenix.controls.JFXButton
import com.jfoenix.controls.JFXListCell
import com.jfoenix.controls.JFXListView
import com.jfoenix.controls.JFXPopup
import com.patres.automation.gui.controller.model.RootSchemaGroupController
import com.patres.automation.menuItem.MenuItem
import com.patres.automation.menuItem.MenuItemGroup
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView
import javafx.collections.FXCollections
import javafx.event.EventHandler
import javafx.scene.Node
import javafx.scene.control.ListView
import javafx.scene.control.Separator
import javafx.util.Callback


class ActionBarController(private val rootSchemaGroupController: RootSchemaGroupController) {

    private var actionBox = rootSchemaGroupController.actionBox

    private val nodeActionMap = HashMap<Node, MenuItem>()
    private val listViews = ArrayList<ListView<MenuItem>>()

    init {
        addActions()
    }

    fun initAfterSetModel() {
        updateDisabledButtons()
        updateActions()
    }

    private fun addActions() {
        MenuItemGroup.values()
                .forEach { menuItemGroup ->
                    menuItemGroup.menuItems
                            .filter { it.parent == null }
                            .forEach { createGroup(it) }
                    addSeparator(menuItemGroup)
                }
    }

    private fun addSeparator(menuItemGroup: MenuItemGroup) {
        if (MenuItemGroup.values().last() != menuItemGroup) {
            actionBox.children.add(Separator())
        }
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
        val model = rootSchemaGroupController.model
        nodeActionMap.forEach { (button, action) ->
            button.isDisable = action.shouldBeDisabled(model)
        }
    }

    private fun updateActions() {
        val model = rootSchemaGroupController.model
        nodeActionMap.forEach { (button, action) ->
            button.onMouseClicked = EventHandler { action.menuItemHandler(model) }
        }
        listViews.forEach { listView ->
            listView.onMouseClicked = EventHandler { listView.selectionModel.selectedItem.menuItemHandler(model) }
        }
    }

}