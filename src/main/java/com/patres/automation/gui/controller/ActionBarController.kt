package com.patres.automation.gui.controller

import com.jfoenix.controls.JFXButton
import com.jfoenix.controls.JFXListCell
import com.jfoenix.controls.JFXListView
import com.jfoenix.controls.JFXPopup
import com.patres.automation.Main
import com.patres.automation.gui.controller.model.RootSchemaGroupController
import com.patres.automation.gui.custom.IconButton
import com.patres.automation.gui.font.FontAutomationIcon
import com.patres.automation.menuItem.MenuItem
import com.patres.automation.menuItem.MenuItemGroup
import com.patres.automation.util.fromBundle
import com.patres.automation.util.getIcon
import javafx.collections.FXCollections
import javafx.event.EventHandler
import javafx.scene.Node
import javafx.scene.control.ListView
import javafx.scene.control.Separator
import javafx.scene.control.Tooltip
import javafx.util.Callback


class ActionBarController(private val rootSchemaGroupController: RootSchemaGroupController) {

    private var actionBox = rootSchemaGroupController.actionBox

    private val nodeActionMap = HashMap<Node, MenuItem>()
    private val listViews = ArrayList<ListView<MenuItem>>()

    private var runButton: Node? = null
    private var stopButton: Node? = null

    init {
        addActions()
        setRunIcon()
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
        val button = IconButton(action.graphic)
        val nestedAction = MenuItem.findAllWithAction(action)
        if (nestedAction.isNotEmpty()) {
            createPopup(nestedAction, button)
        } else {
            nodeActionMap[button] = action

            when (action) {
                MenuItem.RUN -> runButton = button
                MenuItem.STOP -> stopButton = button
                else -> {
                }
            }
        }
        button.tooltip = Tooltip(fromBundle(action.bundleName))
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
                        text = Main.getLanguageString(item.bundleName) // TODO dynamicly change language
                    }
                    graphic = item?.graphic?.getIcon()
                }
            }
        }
        listView.styleClass.add("action-bar")
        val popup = JFXPopup(listView)
        listViews.add(listView)
        button.setOnMouseClicked { popup.show(button, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT, 35.0, 0.0) }
    }

    fun updateDisabledButtons() {
        nodeActionMap.forEach { (button, action) ->
            button.isDisable = action.shouldBeDisabled(rootSchemaGroupController)
        }
    }

    private fun updateActions() {
        val model = rootSchemaGroupController.model
        nodeActionMap.forEach { (button, action) ->
            button.onMouseClicked = EventHandler {
                action.menuItemHandler(model)
                model.changeDetect()
            }

            button.hoverProperty().bean
            action.shouldBeDisabled

        }
        listViews.forEach { listView ->
            listView.onMouseClicked = EventHandler {
                listView.selectionModel.selectedItem.menuItemHandler(model)
                model.changeDetect()
            }
        }
    }

    fun setStopIcon() {
        actionBox.children.remove(runButton)
        if (!actionBox.children.contains(stopButton)) {
            actionBox.children.add(0, stopButton)
        }
    }

    fun setRunIcon() {
        actionBox.children.remove(stopButton)
        if (!actionBox.children.contains(runButton)) {
            actionBox.children.add(0, runButton)
        }
    }

}