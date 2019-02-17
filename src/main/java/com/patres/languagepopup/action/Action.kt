package com.patres.languagepopup.action

import com.patres.languagepopup.Main
import com.patres.languagepopup.model.RootSchemaGroupModel
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import javafx.scene.control.Label

enum class Action(
        val graphic: FontAwesomeIcon?,
        val bundleName: String = "",
        val parent: Action?,
        val group: Boolean,
        val shouldBeDisabled: (rootSchemaGroupModel: RootSchemaGroupModel) -> Boolean = { false },
        val actionHandler: (rootSchemaGroupModel: RootSchemaGroupModel) -> Unit = {}
) {


    MOVE_TO_UP(FontAwesomeIcon.ARROW_UP, "robot.action.moveToUp", null, false, ActionValidators.isNotSelectedActionOrIsRoot, ActionHandlers.moveToUp),
    MOVE_TO_DOWN(FontAwesomeIcon.ARROW_DOWN, "robot.action.moveToDown", null, false, ActionValidators.isNotSelectedActionOrIsRoot, ActionHandlers.moveToDown),
    REMOVE(FontAwesomeIcon.TRASH_ALT, "robot.action.remove", null, false, ActionValidators.isNotSelectedActionOrIsRoot, ActionHandlers.remove),

    ADD_GROUP(FontAwesomeIcon.OBJECT_GROUP, "robot.action.addGroup", null, false, actionHandler = ActionHandlers.addSchemeGroup),

    LEFT_MOUSE_BUTTON(FontAwesomeIcon.MOUSE_POINTER, "robot.action.mouseClick", null, true),
    MIDDLE_MOUSE_BUTTON(FontAwesomeIcon.MOUSE_POINTER, "robot.action.mouseClick", null, true),
    RIGHT_MOUSE_BUTTON(FontAwesomeIcon.MOUSE_POINTER, "robot.action.mouseClick", null, true),

    CLICK_LEFT_MOUSE_BUTTON(FontAwesomeIcon.MOUSE_POINTER, "robot.action.mouseClick", LEFT_MOUSE_BUTTON, false, actionHandler = ActionHandlers.addLeftClickMouse),
    CLICK_MIDDLE_MOUSE_BUTTON(FontAwesomeIcon.MOUSE_POINTER, "robot.action.mouseClick", MIDDLE_MOUSE_BUTTON, false),
    CLICK_RIGHT_MOUSE_BUTTON(FontAwesomeIcon.MOUSE_POINTER, "robot.action.mouseClick", RIGHT_MOUSE_BUTTON, false),
    DOUBLE_CLICK_LEFT_MOUSE_BUTTON(FontAwesomeIcon.MOUSE_POINTER, "robot.action.doubleMouseClick", LEFT_MOUSE_BUTTON, false),
    DOUBLE_CLICK_MIDDLE_MOUSE_BUTTON(FontAwesomeIcon.MOUSE_POINTER, "robot.action.doubleMouseClick", MIDDLE_MOUSE_BUTTON, false),
    DOUBLE_CLICK_RIGHT_MOUSE_BUTTON(FontAwesomeIcon.MOUSE_POINTER, "robot.action.doubleMouseClick", RIGHT_MOUSE_BUTTON, false),
    RELEASE_LEFT_MOUSE_BUTTON(FontAwesomeIcon.MOUSE_POINTER, "robot.action.releaseMouseClick", LEFT_MOUSE_BUTTON, false),
    RELEASE_MIDDLE_MOUSE_BUTTON(FontAwesomeIcon.MOUSE_POINTER, "robot.action.releaseMouseClick", MIDDLE_MOUSE_BUTTON, false),
    RELEASE_RIGHT_MOUSE_BUTTON(FontAwesomeIcon.MOUSE_POINTER, "robot.action.releaseMouseClick", RIGHT_MOUSE_BUTTON, false),
    PRESS_LEFT_MOUSE_BUTTON(FontAwesomeIcon.MOUSE_POINTER, "robot.action.pressMouseClick", LEFT_MOUSE_BUTTON, false),
    PRESS_MIDDLE_MOUSE_BUTTON(FontAwesomeIcon.MOUSE_POINTER, "robot.action.pressMouseClick", MIDDLE_MOUSE_BUTTON, false),
    PRESS_RIGHT_MOUSE_BUTTON(FontAwesomeIcon.MOUSE_POINTER, "robot.action.pressMouseClick", RIGHT_MOUSE_BUTTON, false);

    val actionName = Main.bundle.getString(bundleName)
    val label: Label

    init {
        label = Label(actionName)
    }

    companion object {
        fun findAllWithAction(action: Action) = Action.values().filter { it.parent == action }
    }

}