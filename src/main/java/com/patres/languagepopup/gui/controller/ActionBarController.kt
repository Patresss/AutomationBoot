package com.patres.languagepopup.gui.controller

import com.jfoenix.controls.JFXButton
import com.jfoenix.controls.JFXListView
import com.jfoenix.controls.JFXPopup
import com.patres.languagepopup.Action
import com.patres.languagepopup.gui.controller.model.RootSchemaGroupController
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView
import javafx.scene.control.Label


class ActionBarController(rootSchemaGroupController: RootSchemaGroupController) {

     var actionBox = rootSchemaGroupController.actionBox

    init {
        addActions()
    }

    private fun addActions() {
        val actionToAdd = listOf(
                Action.LEFT_MOUSE_BUTTON,
                Action.MIDDLE_MOUSE_BUTTON,
                Action.RIGHT_MOUSE_BUTTON)
        actionToAdd.forEach { createGroup(it) }
    }

    private fun createGroup(action: Action) {
        val button = createButton(action)
        val nestedAction = Action.findAllWithAction(action)
        val labels = nestedAction.map { it.label }
        val list = JFXListView<Label>()
        list.items.addAll(labels)
        val popup = JFXPopup(list)
        button.setOnMouseClicked { popup.show(button, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT, 35.0, 0.0) }
        actionBox.children.add(button)
    }

    private fun createButton(action: Action): JFXButton {
        return JFXButton().apply {
            styleClass.add("animated-option-button")
            graphic = FontAwesomeIconView(action.graphic).apply { styleClass.add("sub-icon") }
        }
    }

}