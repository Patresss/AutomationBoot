package com.patres.automation.menuItem

import com.patres.automation.Main
import com.patres.automation.action.delay.DelayAction
import com.patres.automation.action.mouse.TextFieldActionModel
import com.patres.automation.action.mouse.click.LeftMouseClickAction
import com.patres.automation.action.mouse.click.MiddleMouseClickAction
import com.patres.automation.action.mouse.click.RightMouseClickAction
import com.patres.automation.action.mouse.doubleClick.LeftDoubleMouseClickAction
import com.patres.automation.action.mouse.doubleClick.MiddleDoubleMouseClickAction
import com.patres.automation.action.mouse.doubleClick.RightDoubleMouseClickAction
import com.patres.automation.action.mouse.move.MoveMouseAction
import com.patres.automation.action.mouse.press.PressLeftMouseAction
import com.patres.automation.action.mouse.press.PressMiddleMouseAction
import com.patres.automation.action.mouse.press.PressRightMouseAction
import com.patres.automation.action.mouse.release.ReleaseLeftMouseAction
import com.patres.automation.action.mouse.release.ReleaseMiddleMouseAction
import com.patres.automation.action.mouse.release.ReleaseRightMouseAction
import com.patres.automation.action.mouse.wheel.ScrollWheelDownAction
import com.patres.automation.action.mouse.wheel.ScrollWheelUpAction
import com.patres.automation.font.FontAutomationIcon
import com.patres.automation.model.RootSchemaGroupModel
import de.jensd.fx.glyphs.GlyphIcons
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import javafx.scene.control.Label

enum class MenuItem(
        val graphic: GlyphIcons?,
        bundleName: String = "",
        val parent: MenuItem?,
        val shouldBeDisabled: (rootSchemaGroupModel: RootSchemaGroupModel) -> Boolean = { false },
        val menuItemHandler: (rootSchemaGroupModel: RootSchemaGroupModel) -> Unit = {},
        val classValue: Class<out TextFieldActionModel>? = null
) {

    RUN(FontAwesomeIcon.PLAY, "robot.action.runAutomation", null, menuItemHandler = MenuItemHandlers.runAutomation),
    MOVE_TO_UP(FontAwesomeIcon.ARROW_UP, "robot.action.moveToUp", null, MenuItemValidators.isNotSelectedActionOrIsRoot, MenuItemHandlers.moveToUp),
    MOVE_TO_DOWN(FontAwesomeIcon.ARROW_DOWN, "robot.action.moveToDown", null, MenuItemValidators.isNotSelectedActionOrIsRoot, MenuItemHandlers.moveToDown),
    REMOVE(FontAwesomeIcon.REMOVE, "robot.action.remove", null, MenuItemValidators.isNotSelectedActionOrIsRoot, MenuItemHandlers.remove),

    ADD_GROUP(FontAwesomeIcon.OBJECT_GROUP, "robot.action.addGroup", null, menuItemHandler = MenuItemHandlers.addSchemeGroup),

    DELAY(FontAwesomeIcon.HOURGLASS, "robot.action.delay", null, menuItemHandler = MenuItemHandlers.addDelay, classValue = DelayAction::class.java),
    MOVE_MOUSE(FontAutomationIcon.MOVE_MOUSE, "robot.action.moveMouse", null, menuItemHandler = MenuItemHandlers.addMoveMouse, classValue = MoveMouseAction::class.java),

    LEFT_MOUSE_BUTTON(FontAutomationIcon.LEFT_MOUSE_BUTTON_EDGE_ALT, "roboto.action.mouse.left", null),
    MIDDLE_MOUSE_BUTTON(FontAutomationIcon.MIDDLE_MOUSE_BUTTON_SMALL, "roboto.action.mouse.middle", null),
    RIGHT_MOUSE_BUTTON(FontAutomationIcon.RIGHT_MOUSE_BUTTON_EDGE_ALT, "roboto.action.mouse.right", null),

    CLICK_LEFT_MOUSE_BUTTON(FontAwesomeIcon.MOUSE_POINTER, "robot.action.mouseClick.left", LEFT_MOUSE_BUTTON, menuItemHandler = MenuItemHandlers.addLeftClickMouse, classValue = LeftMouseClickAction::class.java),
    CLICK_MIDDLE_MOUSE_BUTTON(FontAwesomeIcon.MOUSE_POINTER, "robot.action.mouseClick.middle", MIDDLE_MOUSE_BUTTON, menuItemHandler = MenuItemHandlers.addMiddleClickMouse, classValue = MiddleMouseClickAction::class.java),
    CLICK_RIGHT_MOUSE_BUTTON(FontAwesomeIcon.MOUSE_POINTER, "robot.action.mouseClick.right", RIGHT_MOUSE_BUTTON, menuItemHandler = MenuItemHandlers.addRightClickMouse, classValue = RightMouseClickAction::class.java),
    DOUBLE_CLICK_LEFT_MOUSE_BUTTON(FontAwesomeIcon.MOUSE_POINTER, "robot.action.doubleMouseClick.left", LEFT_MOUSE_BUTTON, menuItemHandler = MenuItemHandlers.addDoubleLeftClickMouse, classValue = LeftDoubleMouseClickAction::class.java),
    DOUBLE_CLICK_MIDDLE_MOUSE_BUTTON(FontAwesomeIcon.MOUSE_POINTER, "robot.action.doubleMouseClick.middle", MIDDLE_MOUSE_BUTTON, menuItemHandler = MenuItemHandlers.addDoubleMiddleClickMouse, classValue = MiddleDoubleMouseClickAction::class.java),
    DOUBLE_CLICK_RIGHT_MOUSE_BUTTON(FontAwesomeIcon.MOUSE_POINTER, "robot.action.doubleMouseClick.right", RIGHT_MOUSE_BUTTON, menuItemHandler = MenuItemHandlers.addDoubleRightClickMouse, classValue = RightDoubleMouseClickAction::class.java),
    PRESS_LEFT_MOUSE_BUTTON(FontAwesomeIcon.MOUSE_POINTER, "robot.action.pressMouseClick.left", LEFT_MOUSE_BUTTON, menuItemHandler = MenuItemHandlers.addPressLeftMouse, classValue = PressLeftMouseAction::class.java),
    PRESS_MIDDLE_MOUSE_BUTTON(FontAwesomeIcon.MOUSE_POINTER, "robot.action.pressMouseClick.middle", MIDDLE_MOUSE_BUTTON, menuItemHandler = MenuItemHandlers.addPressMiddleMouse, classValue = PressMiddleMouseAction::class.java),
    PRESS_RIGHT_MOUSE_BUTTON(FontAwesomeIcon.MOUSE_POINTER, "robot.action.pressMouseClick.right", RIGHT_MOUSE_BUTTON, menuItemHandler = MenuItemHandlers.addPressRightMouse, classValue = PressRightMouseAction::class.java),
    RELEASE_LEFT_MOUSE_BUTTON(FontAwesomeIcon.MOUSE_POINTER, "robot.action.releaseMouseClick.left", LEFT_MOUSE_BUTTON, menuItemHandler = MenuItemHandlers.addReleaseLeftMouse, classValue = ReleaseLeftMouseAction::class.java),
    RELEASE_MIDDLE_MOUSE_BUTTON(FontAwesomeIcon.MOUSE_POINTER, "robot.action.releaseMouseClick.middle", MIDDLE_MOUSE_BUTTON, menuItemHandler = MenuItemHandlers.addReleaseMiddlesMouse, classValue = ReleaseMiddleMouseAction::class.java),
    RELEASE_RIGHT_MOUSE_BUTTON(FontAwesomeIcon.MOUSE_POINTER, "robot.action.releaseMouseClick.right", RIGHT_MOUSE_BUTTON, menuItemHandler = MenuItemHandlers.addReleaseRightoMouse, classValue = ReleaseRightMouseAction::class.java),
    SCROLL_WHEEL_UP(FontAwesomeIcon.MOUSE_POINTER, "robot.action.scrollWheel.up", MIDDLE_MOUSE_BUTTON, menuItemHandler = MenuItemHandlers.addScrollWheelUp, classValue = ScrollWheelUpAction::class.java),
    SCROLL_WHEEL_DOWN(FontAwesomeIcon.MOUSE_POINTER, "robot.action.scrollWheel.down", MIDDLE_MOUSE_BUTTON, menuItemHandler = MenuItemHandlers.addScrollWheelDown, classValue = ScrollWheelDownAction::class.java);

    val actionName = if (bundleName.isBlank()) "" else Main.bundle.getString(bundleName) ?: ""
    val label: Label

    init {
        label = Label(actionName)
    }

    companion object {
        fun findAllWithAction(action: MenuItem) = MenuItem.values().filter { it.parent == action }
    }

}

