package com.patres.languagepopup.gui.controller

import com.jfoenix.controls.JFXButton
import com.jfoenix.controls.JFXListView
import com.jfoenix.controls.JFXPopup
import com.patres.languagepopup.action.Action
import com.patres.languagepopup.gui.controller.model.RootSchemaGroupController
import com.patres.languagepopup.model.RootSchemaGroupModel
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView
import javafx.event.EventHandler
import javafx.scene.control.Button
import javafx.scene.control.Label


class ActionBarController(val rootSchemaGroupController: RootSchemaGroupController) {

    var actionBox = rootSchemaGroupController.actionBox

    val buttonActionMap = HashMap<Button, Action>()

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
        }
        actionBox.children.add(button)

        buttonActionMap[button] = action
    }

    private fun createPopup(nestedAction: List<Action>, button: JFXButton) {
        val labels = nestedAction.map { it.label }
        val list = JFXListView<Label>()
        list.items.addAll(labels)
        val popup = JFXPopup(list)
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
            buttonActionMap.forEach { (button, action) ->
                button.isDisable = action.shouldBeDisabled(model)
            }
        }
    }

    fun updateActions() {
        rootModel?.let { model ->
            buttonActionMap.forEach { (button, action) ->
                button.onAction = EventHandler { action.actionHandler(model) }
            }
        }
    }

}