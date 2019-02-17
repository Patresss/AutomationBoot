package com.patres.languagepopup.gui.controller

import com.jfoenix.controls.JFXButton
import com.jfoenix.controls.JFXListCell
import com.jfoenix.controls.JFXListView
import com.jfoenix.controls.JFXPopup
import com.patres.languagepopup.action.Action
import com.patres.languagepopup.gui.controller.model.RootSchemaGroupController
import com.patres.languagepopup.model.RootSchemaGroupModel
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView
import javafx.collections.FXCollections
import javafx.event.EventHandler
import javafx.scene.Node
import javafx.scene.control.Label
import javafx.scene.control.ListCell
import javafx.scene.control.ListView
import javafx.scene.input.MouseEvent
import javafx.util.Callback


class ActionBarController(val rootSchemaGroupController: RootSchemaGroupController) {

    var actionBox = rootSchemaGroupController.actionBox

    val nodeActionMap = HashMap<Node, Action>()
    val listViews = ArrayList<ListView<Action>>()

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
        val actionToAdd = listOf(
                Action.MOVE_TO_UP,
                Action.MOVE_TO_DOWN,
                Action.REMOVE,

                Action.ADD_GROUP,

                Action.LEFT_MOUSE_BUTTON,
                Action.MIDDLE_MOUSE_BUTTON,
                Action.RIGHT_MOUSE_BUTTON)
        actionToAdd.forEach { createGroup(it) }
    }

    private fun createGroup(action: Action) {
        val button = createButton(action)
        val nestedAction = Action.findAllWithAction(action)
        if (!nestedAction.isEmpty()) {
            createPopup(nestedAction, button)
        } else {
            nodeActionMap[button] = action
        }
        actionBox.children.add(button)

    }

    private fun createPopup(nestedAction: List<Action>, button: JFXButton) {
        val actions = FXCollections.observableArrayList<Action>(nestedAction)
        val listView = JFXListView<Action>().apply { items = actions }

        listView.cellFactory = Callback {
            object : JFXListCell<Action>() {
                override fun updateItem(item: Action?, empty: Boolean) {
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

    private fun createButton(action: Action): JFXButton {
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
                button.onMouseClicked = EventHandler { action.actionHandler(model) }
            }
            listViews.forEach {listView ->
                listView.onMouseClicked = EventHandler { listView.selectionModel.selectedItem.actionHandler(model) }
            }
        }
    }

}