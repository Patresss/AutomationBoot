package com.patres.automation.gui.menuItem

import com.patres.automation.action.RootSchemaGroupModel
import com.patres.automation.gui.controller.model.RootSchemaGroupController
import com.patres.automation.gui.font.FontAutomationIcon
import com.patres.automation.type.*
import de.jensd.fx.glyphs.GlyphIcons
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon

enum class MenuItem(
        val graphic: GlyphIcons,
        val actionBoot: ActionBootable? = null,
        val parent: MenuItem? = null,
        val bundleName: String = actionBoot?.bundleName() ?: "",
        val shouldBeDisabled: (controller: RootSchemaGroupController) -> Boolean = { false },
        val menuItemHandler: (rootSchemaGroupModel: RootSchemaGroupModel) -> Unit = actionBoot?.addController() ?: {},
        val actionGraphic: GlyphIcons = graphic
) {

    RUN(FontAwesomeIcon.PLAY, null, null, "robot.action.runAutomation", menuItemHandler = MenuItemHandlers.runAutomation),
    STOP(FontAwesomeIcon.STOP, null, null, "robot.action.stop", menuItemHandler = MenuItemHandlers.stopAutomation),
    MOVE_TO_UP(FontAwesomeIcon.ARROW_UP, null, null, "robot.action.moveToUp", MenuItemValidators.isNotSelectedActionOrIsRoot, MenuItemHandlers.moveToUp),
    MOVE_TO_DOWN(FontAwesomeIcon.ARROW_DOWN, null, null, "robot.action.moveToDown", MenuItemValidators.isNotSelectedActionOrIsRoot, MenuItemHandlers.moveToDown),
    REMOVE(FontAwesomeIcon.REMOVE, null, null, "robot.action.remove", MenuItemValidators.isNotSelectedActionOrIsRoot, MenuItemHandlers.remove),

    ADD_GROUP(FontAwesomeIcon.OBJECT_GROUP, ActionBootSchema.ADD_GROUP, null),

    DELAY_ITEM(FontAwesomeIcon.HOURGLASS, ActionBootTextField.DELAY, null),
    MOVE_MOUSE(FontAutomationIcon.MOVE_MOUSE, ActionBootMousePoint.MOVE_MOUSE, null),

    LEFT_MOUSE_BUTTON(FontAutomationIcon.LEFT_MOUSE_BUTTON_EDGE_ALT, bundleName = "roboto.action.mouse.left"),
    MIDDLE_MOUSE_BUTTON(FontAutomationIcon.MIDDLE_MOUSE_BUTTON_SMALL, bundleName = "roboto.action.mouse.middle"),
    RIGHT_MOUSE_BUTTON(FontAutomationIcon.RIGHT_MOUSE_BUTTON_EDGE_ALT, bundleName = "roboto.action.mouse.right"),
    KEYBOARD(FontAwesomeIcon.KEYBOARD_ALT, bundleName = "robot.action.keyboard"),
    SCRIPT(FontAwesomeIcon.TERMINAL, bundleName = "robot.action.scripts"),

    CLICK_LEFT_MOUSE_BUTTON(FontAwesomeIcon.MOUSE_POINTER, ActionBootMousePoint.CLICK_LEFT_MOUSE_BUTTON, LEFT_MOUSE_BUTTON, actionGraphic = FontAutomationIcon.LEFT_MOUSE_BUTTON_EDGE_ALT),
    CLICK_MIDDLE_MOUSE_BUTTON(FontAwesomeIcon.MOUSE_POINTER, ActionBootMousePoint.CLICK_MIDDLE_MOUSE_BUTTON, MIDDLE_MOUSE_BUTTON, actionGraphic = FontAutomationIcon.MIDDLE_MOUSE_BUTTON),
    CLICK_RIGHT_MOUSE_BUTTON(FontAwesomeIcon.MOUSE_POINTER, ActionBootMousePoint.CLICK_RIGHT_MOUSE_BUTTON, RIGHT_MOUSE_BUTTON, actionGraphic = FontAutomationIcon.RIGHT_MOUSE_BUTTON),

    DOUBLE_CLICK_LEFT_MOUSE_BUTTON(FontAwesomeIcon.MOUSE_POINTER, ActionBootMousePoint.DOUBLE_CLICK_LEFT_MOUSE_BUTTON, LEFT_MOUSE_BUTTON, actionGraphic = FontAutomationIcon.LEFT_MOUSE_BUTTON_EDGE_ALT),
    DOUBLE_CLICK_MIDDLE_MOUSE_BUTTON(FontAwesomeIcon.MOUSE_POINTER, ActionBootMousePoint.DOUBLE_CLICK_MIDDLE_MOUSE_BUTTON, MIDDLE_MOUSE_BUTTON, actionGraphic = FontAutomationIcon.MIDDLE_MOUSE_BUTTON),
    DOUBLE_CLICK_RIGHT_MOUSE_BUTTON(FontAwesomeIcon.MOUSE_POINTER, ActionBootMousePoint.DOUBLE_CLICK_RIGHT_MOUSE_BUTTON, RIGHT_MOUSE_BUTTON, actionGraphic = FontAutomationIcon.RIGHT_MOUSE_BUTTON),

    PRESS_LEFT_MOUSE_BUTTON(FontAwesomeIcon.MOUSE_POINTER, ActionBootMousePoint.PRESS_LEFT_MOUSE_BUTTON, LEFT_MOUSE_BUTTON, actionGraphic = FontAutomationIcon.LEFT_MOUSE_BUTTON_EDGE_ALT),
    PRESS_MIDDLE_MOUSE_BUTTON(FontAwesomeIcon.MOUSE_POINTER, ActionBootMousePoint.PRESS_MIDDLE_MOUSE_BUTTON, MIDDLE_MOUSE_BUTTON, actionGraphic = FontAutomationIcon.MIDDLE_MOUSE_BUTTON),
    PRESS_RIGHT_MOUSE_BUTTON(FontAwesomeIcon.MOUSE_POINTER, ActionBootMousePoint.PRESS_RIGHT_MOUSE_BUTTON, RIGHT_MOUSE_BUTTON, actionGraphic = FontAutomationIcon.RIGHT_MOUSE_BUTTON),

    RELEASE_LEFT_MOUSE_BUTTON(FontAwesomeIcon.MOUSE_POINTER, ActionBootMousePoint.RELEASE_LEFT_MOUSE_BUTTON, LEFT_MOUSE_BUTTON, actionGraphic = FontAutomationIcon.LEFT_MOUSE_BUTTON_EDGE_ALT),
    RELEASE_MIDDLE_MOUSE_BUTTON(FontAwesomeIcon.MOUSE_POINTER, ActionBootMousePoint.RELEASE_MIDDLE_MOUSE_BUTTON, MIDDLE_MOUSE_BUTTON, actionGraphic = FontAutomationIcon.MIDDLE_MOUSE_BUTTON),
    RELEASE_RIGHT_MOUSE_BUTTON(FontAwesomeIcon.MOUSE_POINTER, ActionBootMousePoint.RELEASE_RIGHT_MOUSE_BUTTON, RIGHT_MOUSE_BUTTON, actionGraphic = FontAutomationIcon.RIGHT_MOUSE_BUTTON),
    SCROLL_WHEEL_UP(FontAwesomeIcon.CARET_UP, ActionBootTextField.SCROLL_WHEEL_UP, MIDDLE_MOUSE_BUTTON),
    SCROLL_WHEEL_DOWN(FontAwesomeIcon.CARET_DOWN, ActionBootTextField.SCROLL_WHEEL_DOWN, MIDDLE_MOUSE_BUTTON),

    PASTE_TEXT(FontAwesomeIcon.PASTE, ActionBootTextArea.PASTE_TEXT, KEYBOARD),
    PASTE_TEXT_FROM_FILE(FontAwesomeIcon.PASTE, ActionBootBrowser.PASTE_TEXT_FROM_FILE, KEYBOARD),
    TYPE_TEXT(FontAwesomeIcon.KEYBOARD_ALT, ActionBootTextArea.TYPE_TEXT, KEYBOARD),
    TYPE_TEXT_FROM_FILE(FontAwesomeIcon.KEYBOARD_ALT, ActionBootBrowser.TYPE_TEXT_FROM_FILE, KEYBOARD),
    PRESS_KEYBOARD_BUTTON(FontAwesomeIcon.KEYBOARD_ALT, ActionBootKeyboard.PRESS_KEYBOARD_BUTTON, KEYBOARD),

    OPEN_FILE(FontAwesomeIcon.FILE, ActionBootBrowser.OPEN_FILE, SCRIPT),
    OPEN_DIRECTORY(FontAwesomeIcon.FOLDER, ActionBootBrowser.OPEN_DIRECTORY, SCRIPT),
    WINDOWS_SCRIPT_RUN(FontAwesomeIcon.TERMINAL, ActionBootBrowser.WINDOWS_SCRIPT_RUN, SCRIPT),
    WINDOWS_SCRIPT_RUN_AND_WAITE(FontAwesomeIcon.TERMINAL, ActionBootBrowser.WINDOWS_SCRIPT_RUN_AND_WAITE, SCRIPT),

    SETTINGS(FontAwesomeIcon.GEAR, null, null, "menu.settings.localSettings", menuItemHandler = { root: RootSchemaGroupModel -> root.controller.openLocalSettings() });

    companion object {
        fun findAllWithAction(action: MenuItem) = MenuItem.values().filter { it.parent == action }
        inline fun <reified T : ActionBootable> findAllWithThisSameActionType() = MenuItem.values().map { it.actionBoot }.filterIsInstance<T>()
    }

}

