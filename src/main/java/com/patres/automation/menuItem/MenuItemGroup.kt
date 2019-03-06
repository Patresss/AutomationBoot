package com.patres.automation.menuItem



enum class MenuItemGroup(
        val menuItems: List<MenuItem>
){

   RUN(listOf(MenuItem.RUN)),
   SELECTED_ACTION(listOf(MenuItem.MOVE_TO_UP, MenuItem.MOVE_TO_DOWN, MenuItem.REMOVE)),
   OTHER(listOf(MenuItem.ADD_GROUP, MenuItem.DELAY)),
   MOUSE(listOf(MenuItem.MOVE_MOUSE, MenuItem.LEFT_MOUSE_BUTTON, MenuItem.MIDDLE_MOUSE_BUTTON, MenuItem.RIGHT_MOUSE_BUTTON)),
   KEYBOARD(listOf(MenuItem.KEYBOARD))

}