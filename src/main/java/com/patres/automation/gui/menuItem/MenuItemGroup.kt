package com.patres.automation.gui.menuItem


enum class MenuItemGroup(
        val menuItems: List<MenuItem>
) {

    RUN(listOf(MenuItem.RUN, MenuItem.STOP)),
    SELECTED_ACTION(listOf(MenuItem.MOVE_TO_UP, MenuItem.MOVE_TO_DOWN, MenuItem.REMOVE, MenuItem.START_RECORD, MenuItem.STOP_RECORD)),
    OTHER(listOf(MenuItem.ADD_GROUP, MenuItem.DELAY_ITEM)),
    MOUSE(listOf(MenuItem.MOVE_MOUSE, MenuItem.LEFT_MOUSE_BUTTON, MenuItem.MIDDLE_MOUSE_BUTTON, MenuItem.RIGHT_MOUSE_BUTTON)),
    KEYBOARD(listOf(MenuItem.KEYBOARD)),
    SCRIPT(listOf(MenuItem.SCRIPT)),
    SETTINGS(listOf(MenuItem.SETTINGS))

}