package com.patres.languagepopup

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import javafx.scene.control.Label

enum class Action(
        val graphic: FontAwesomeIcon?,
        val bundleName: String = "",
        val parent: Action?,
        val group:Boolean
){



    LEFT_MOUSE_BUTTON(FontAwesomeIcon.MOUSE_POINTER, "robot.action.mouseClick", null, true),
    MIDDLE_MOUSE_BUTTON(FontAwesomeIcon.MOUSE_POINTER, "robot.action.mouseClick", null, true),
    RIGHT_MOUSE_BUTTON(FontAwesomeIcon.MOUSE_POINTER, "robot.action.mouseClick", null, true),

    CLICK_LEFT_MOUSE_BUTTON(FontAwesomeIcon.MOUSE_POINTER, "robot.action.mouseClick", Action.LEFT_MOUSE_BUTTON, false),
    CLICK_MIDDLE_MOUSE_BUTTON(FontAwesomeIcon.MOUSE_POINTER, "robot.action.mouseClick", Action.MIDDLE_MOUSE_BUTTON, false),
    CLICK_RIGHT_MOUSE_BUTTON(FontAwesomeIcon.MOUSE_POINTER, "robot.action.mouseClick", Action.RIGHT_MOUSE_BUTTON, false),
    DOUBLE_CLICK_LEFT_MOUSE_BUTTON(FontAwesomeIcon.MOUSE_POINTER, "robot.action.doubleMouseClick", Action.LEFT_MOUSE_BUTTON, false),
    DOUBLE_CLICK_MIDDLE_MOUSE_BUTTON(FontAwesomeIcon.MOUSE_POINTER, "robot.action.doubleMouseClick", Action.MIDDLE_MOUSE_BUTTON, false),
    DOUBLE_CLICK_RIGHT_MOUSE_BUTTON(FontAwesomeIcon.MOUSE_POINTER, "robot.action.doubleMouseClick", Action.RIGHT_MOUSE_BUTTON, false),
    RELEASE_LEFT_MOUSE_BUTTON(FontAwesomeIcon.MOUSE_POINTER, "robot.action.releaseMouseClick", Action.LEFT_MOUSE_BUTTON, false),
    RELEASE_MIDDLE_MOUSE_BUTTON(FontAwesomeIcon.MOUSE_POINTER, "robot.action.releaseMouseClick", Action.MIDDLE_MOUSE_BUTTON, false),
    RELEASE_RIGHT_MOUSE_BUTTON(FontAwesomeIcon.MOUSE_POINTER, "robot.action.releaseMouseClick", Action.RIGHT_MOUSE_BUTTON, false),
    PRESS_LEFT_MOUSE_BUTTON(FontAwesomeIcon.MOUSE_POINTER, "robot.action.pressMouseClick", Action.LEFT_MOUSE_BUTTON, false),
    PRESS_MIDDLE_MOUSE_BUTTON(FontAwesomeIcon.MOUSE_POINTER, "robot.action.pressMouseClick", Action.MIDDLE_MOUSE_BUTTON, false),
    PRESS_RIGHT_MOUSE_BUTTON(FontAwesomeIcon.MOUSE_POINTER, "robot.action.pressMouseClick", Action.RIGHT_MOUSE_BUTTON, false);

    val actionName = Main.bundle.getString(bundleName)
    val label: Label

    init {
        label = Label(actionName)
    }

    companion object {
         fun findAllWithAction(action: Action) = Action.values().filter { it.parent == action }
    }
}