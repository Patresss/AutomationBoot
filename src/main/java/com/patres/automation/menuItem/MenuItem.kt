package com.patres.automation.menuItem

import com.patres.automation.Main
import com.patres.automation.action.script.OpenFileOrDirectoryAction
import com.patres.automation.action.script.WindowsRunAndWaitScriptAction
import com.patres.automation.action.script.WindowsRunScriptAction
import com.patres.automation.action.script.WindowsScriptAction
import com.patres.automation.action.text.paste.PasteTextFromFieldAction
import com.patres.automation.action.text.paste.PasteTextFromFileAction
import com.patres.automation.action.text.type.TypeTextFromFieldAction
import com.patres.automation.action.text.type.TypeTextFromFileAction
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
        val menuItemHandler: (rootSchemaGroupModel: RootSchemaGroupModel) -> Unit = {}
) {

    RUN(FontAwesomeIcon.PLAY, "robot.action.runAutomation", null, menuItemHandler = MenuItemHandlers.runAutomation),
    MOVE_TO_UP(FontAwesomeIcon.ARROW_UP, "robot.action.moveToUp", null, MenuItemValidators.isNotSelectedActionOrIsRoot, MenuItemHandlers.moveToUp),
    MOVE_TO_DOWN(FontAwesomeIcon.ARROW_DOWN, "robot.action.moveToDown", null, MenuItemValidators.isNotSelectedActionOrIsRoot, MenuItemHandlers.moveToDown),
    REMOVE(FontAwesomeIcon.REMOVE, "robot.action.remove", null, MenuItemValidators.isNotSelectedActionOrIsRoot, MenuItemHandlers.remove),

    ADD_GROUP(FontAwesomeIcon.OBJECT_GROUP, "robot.action.addGroup", null, menuItemHandler = MenuItemHandlers.addSchemeGroup),

    DELAY(FontAwesomeIcon.HOURGLASS, "robot.action.delay", null, menuItemHandler = MenuItemHandlers.addDelay),
    MOVE_MOUSE(FontAutomationIcon.MOVE_MOUSE, "robot.action.moveMouse", null, menuItemHandler = MenuItemHandlers.addMoveMouse),

    LEFT_MOUSE_BUTTON(FontAutomationIcon.LEFT_MOUSE_BUTTON_EDGE_ALT, "roboto.action.mouse.left", null),
    MIDDLE_MOUSE_BUTTON(FontAutomationIcon.MIDDLE_MOUSE_BUTTON_SMALL, "roboto.action.mouse.middle", null),
    RIGHT_MOUSE_BUTTON(FontAutomationIcon.RIGHT_MOUSE_BUTTON_EDGE_ALT, "roboto.action.mouse.right", null),
    KEYBOARD(FontAwesomeIcon.KEYBOARD_ALT, "robot.action.keyboard", null),
    SCRIPT(FontAwesomeIcon.TERMINAL, "robot.action.scripts", null),

    CLICK_LEFT_MOUSE_BUTTON(FontAwesomeIcon.MOUSE_POINTER, "robot.action.mouseClick.left", LEFT_MOUSE_BUTTON, menuItemHandler = MenuItemHandlers.addLeftClickMouse),
    CLICK_MIDDLE_MOUSE_BUTTON(FontAwesomeIcon.MOUSE_POINTER, "robot.action.mouseClick.middle", MIDDLE_MOUSE_BUTTON, menuItemHandler = MenuItemHandlers.addMiddleClickMouse),
    CLICK_RIGHT_MOUSE_BUTTON(FontAwesomeIcon.MOUSE_POINTER, "robot.action.mouseClick.right", RIGHT_MOUSE_BUTTON, menuItemHandler = MenuItemHandlers.addRightClickMouse),

    DOUBLE_CLICK_LEFT_MOUSE_BUTTON(FontAwesomeIcon.MOUSE_POINTER, "robot.action.doubleMouseClick.left", LEFT_MOUSE_BUTTON, menuItemHandler = MenuItemHandlers.addDoubleLeftClickMouse),
    DOUBLE_CLICK_MIDDLE_MOUSE_BUTTON(FontAwesomeIcon.MOUSE_POINTER, "robot.action.doubleMouseClick.middle", MIDDLE_MOUSE_BUTTON, menuItemHandler = MenuItemHandlers.addDoubleMiddleClickMouse),
    DOUBLE_CLICK_RIGHT_MOUSE_BUTTON(FontAwesomeIcon.MOUSE_POINTER, "robot.action.doubleMouseClick.right", RIGHT_MOUSE_BUTTON, menuItemHandler = MenuItemHandlers.addDoubleRightClickMouse),

    PRESS_LEFT_MOUSE_BUTTON(FontAwesomeIcon.MOUSE_POINTER, "robot.action.pressMouseClick.left", LEFT_MOUSE_BUTTON, menuItemHandler = MenuItemHandlers.addPressLeftMouse),
    PRESS_MIDDLE_MOUSE_BUTTON(FontAwesomeIcon.MOUSE_POINTER, "robot.action.pressMouseClick.middle", MIDDLE_MOUSE_BUTTON, menuItemHandler = MenuItemHandlers.addPressMiddleMouse),
    PRESS_RIGHT_MOUSE_BUTTON(FontAwesomeIcon.MOUSE_POINTER, "robot.action.pressMouseClick.right", RIGHT_MOUSE_BUTTON, menuItemHandler = MenuItemHandlers.addPressRightMouse),

    RELEASE_LEFT_MOUSE_BUTTON(FontAwesomeIcon.MOUSE_POINTER, "robot.action.releaseMouseClick.left", LEFT_MOUSE_BUTTON, menuItemHandler = MenuItemHandlers.addReleaseLeftMouse),
    RELEASE_MIDDLE_MOUSE_BUTTON(FontAwesomeIcon.MOUSE_POINTER, "robot.action.releaseMouseClick.middle", MIDDLE_MOUSE_BUTTON, menuItemHandler = MenuItemHandlers.addReleaseMiddlesMouse),
    RELEASE_RIGHT_MOUSE_BUTTON(FontAwesomeIcon.MOUSE_POINTER, "robot.action.releaseMouseClick.right", RIGHT_MOUSE_BUTTON, menuItemHandler = MenuItemHandlers.addReleaseRightoMouse),
    SCROLL_WHEEL_UP(FontAwesomeIcon.CARET_UP, "robot.action.scrollWheel.up", MIDDLE_MOUSE_BUTTON, menuItemHandler = MenuItemHandlers.addScrollWheelUp),
    SCROLL_WHEEL_DOWN(FontAwesomeIcon.CARET_DOWN, "robot.action.scrollWheel.down", MIDDLE_MOUSE_BUTTON, menuItemHandler = MenuItemHandlers.addScrollWheelDown),

    PASTE_TEXT(FontAwesomeIcon.PASTE, "robot.action.keyboard.paste", KEYBOARD, menuItemHandler = PasteTextFromFieldAction.addAction),
    PASTE_TEXT_FROM_FILE(FontAwesomeIcon.PASTE, "robot.action.keyboard.paste.file", KEYBOARD, menuItemHandler = PasteTextFromFileAction.addAction),
    TYPE_TEXT(FontAwesomeIcon.KEYBOARD_ALT, "robot.action.keyboard.type", KEYBOARD, menuItemHandler = TypeTextFromFieldAction.addAction),
    TYPE_TEXT_FROM_FILE(FontAwesomeIcon.KEYBOARD_ALT, "robot.action.keyboard.type.file", KEYBOARD, menuItemHandler = TypeTextFromFileAction.addAction),
    PRESS_KEYBOARD_BUTTON(FontAwesomeIcon.KEYBOARD_ALT, "robot.action.keyboard.press", KEYBOARD, menuItemHandler = MenuItemHandlers.addPressKeyboardButton),

    OPEN_FILE_OR_DIRECTORY(FontAwesomeIcon.FILE, "robot.action.open.fileOrDirectory", SCRIPT, menuItemHandler = OpenFileOrDirectoryAction.addAction),
    WINDOWS_SCRIPT_RUN(FontAwesomeIcon.TERMINAL, "robot.action.script.windows.run", SCRIPT, menuItemHandler = WindowsRunScriptAction.addAction),
    WINDOWS_SCRIPT_RUN_AND_WAITE(FontAwesomeIcon.TERMINAL, "robot.action.script.windows.runAndWait", SCRIPT, menuItemHandler = WindowsRunAndWaitScriptAction.addAction);

    val actionName = if (bundleName.isBlank()) "" else Main.bundle.getString(bundleName) ?: ""
    val label: Label

    init {
        label = Label(actionName)
    }

    companion object {
        fun findAllWithAction(action: MenuItem) = MenuItem.values().filter { it.parent == action }
    }

}

