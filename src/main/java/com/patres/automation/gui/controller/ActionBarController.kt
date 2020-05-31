package com.patres.automation.gui.controller

import com.jfoenix.controls.JFXButton
import com.jfoenix.controls.JFXListView
import com.jfoenix.controls.JFXPopup
import com.jfoenix.skins.JFXPopupSkin
import com.patres.automation.gui.controller.model.RootSchemaGroupController
import com.patres.automation.gui.custom.IconButton
import com.patres.automation.gui.menuItem.MenuItem
import com.patres.automation.gui.menuItem.MenuItemGroup
import com.patres.automation.settings.LanguageManager
import com.patres.automation.util.extension.getIcon
import javafx.application.Platform
import javafx.collections.FXCollections
import javafx.event.EventHandler
import javafx.scene.Node
import javafx.scene.control.ListCell
import javafx.scene.control.ListView
import javafx.scene.control.Separator
import javafx.scene.control.Tooltip
import javafx.scene.effect.DropShadow
import javafx.util.Callback


class ActionBarController(private val rootSchemaGroupController: RootSchemaGroupController) {

    private var actionBox = rootSchemaGroupController.actionBox

    private val nodeActionMap = HashMap<Node, MenuItem>()
    private val listViews = ArrayList<ListView<MenuItem>>()

    private var runActionsButton: Node? = null
    private var stopActionsButton: Node? = null
    private var runRecordButton: Node? = null
    private var stopRecordButton: Node? = null

    init {
        addActions()
        setRunIcon()

        initRecordButtonListener()
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
        val nestedAction = MenuItem.findAllWithAction(action).filter { it.shouldBeVisible }
        if (nestedAction.isNotEmpty()) {
            createPopup(nestedAction, button)
        } else {
            nodeActionMap[button] = action

            when (action) {
                MenuItem.RUN -> runActionsButton = button
                MenuItem.STOP -> stopActionsButton = button
                MenuItem.START_RECORD -> runRecordButton = button
                MenuItem.STOP_RECORD -> stopRecordButton = button
                else -> {
                }
            }
        }
        button.tooltip = Tooltip().apply { textProperty().bind(LanguageManager.createStringBinding(action.bundleName)) }
        actionBox.children.add(button)
    }

    private fun createPopup(nestedAction: List<MenuItem>, button: JFXButton) {
        val actions = FXCollections.observableArrayList(nestedAction)
        val listView = JFXListView<MenuItem>().apply { items = actions }

        listView.cellFactory = Callback {
            object : ListCell<MenuItem>() {  // add JFX component JFXListCell if the issue "A bound value cannot be set." will be fixed
                override fun updateItem(item: MenuItem?, empty: Boolean) {
                    super.updateItem(item, empty)
                    if (item != null) {
                        textProperty().bind(LanguageManager.createStringBinding(item.bundleName))
                        graphic = item.graphic.getIcon()
                    }
                }
            }
        }
        listView.styleClass.add("action-bar")
        val popup = JFXPopup(listView)
        listViews.add(listView)
        popup.setOnShown {
            // workaround to disable shadow on the left (buttons cannot be clicked)
            ((popup.skin as JFXPopupSkin?)?.node?.effect as DropShadow?)?.widthProperty()?.value = 15.0
        }
        button.setOnMouseClicked { popup.show(button, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT, 36.0, 0.0) }
    }

    fun updateDisabledButtons() {
        nodeActionMap.forEach { (button, action) ->
            button.isDisable = action.shouldBeDisabled(rootSchemaGroupController)
        }
    }

    private fun updateActions() {
        nodeActionMap.forEach { (button, action) ->
            button.onMouseClicked = EventHandler {
                action.menuItemHandler(rootSchemaGroupController)
                if (action.enabledForChangeDetector) {
                    rootSchemaGroupController.changeDetect()
                }
            }

            button.hoverProperty().bean
            action.shouldBeDisabled

        }
        listViews.forEach { listView ->
            listView.onMouseClicked = EventHandler {
                listView.selectionModel.selectedItem.menuItemHandler(rootSchemaGroupController)
                rootSchemaGroupController.changeDetect()
            }
        }
    }

    fun setStopIcon() {
        actionBox.children.remove(runActionsButton)
        if (!actionBox.children.contains(stopActionsButton)) {
            actionBox.children.add(0, stopActionsButton)
        }
    }

    fun setRunIcon() {
        actionBox.children.remove(stopActionsButton)
        if (!actionBox.children.contains(runActionsButton)) {
            actionBox.children.add(0, runActionsButton)
        }
    }

    private fun initRecordButtonListener() {
        setRunRecordIcon()
        rootSchemaGroupController.actionRecorder.recordRunningProperty.addListener { _, _, newValue ->
            Platform.runLater {
                if (newValue) {
                    setStopRecordIcon()
                } else {
                    setRunRecordIcon()
                }
            }
        }
    }

    private fun setStopRecordIcon() {
        val buttonIndex = actionBox.children.indexOf(runRecordButton)
        actionBox.children.remove(runRecordButton)
        if (!actionBox.children.contains(stopRecordButton)) {
            actionBox.children.add(buttonIndex, stopRecordButton)
        }
    }

    private fun setRunRecordIcon() {
        val buttonIndex = actionBox.children.indexOf(stopRecordButton)
        actionBox.children.remove(stopRecordButton)
        if (!actionBox.children.contains(runRecordButton)) {
            actionBox.children.add(buttonIndex, runRecordButton)
        }
    }

}