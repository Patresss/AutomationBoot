package com.patres.languagepopup.menuItem

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView
import javafx.scene.Node

enum class MenuItemGroup(
        val graphic: Node?
){

   LEFT_MOUSE_BUTTON(FontAwesomeIconView(FontAwesomeIcon.MOUSE_POINTER)),
   MIDDLE_MOUSE_BUTTON(FontAwesomeIconView(FontAwesomeIcon.MOUSE_POINTER)),
   RIGHT_MOUSE_BUTTON(FontAwesomeIconView(FontAwesomeIcon.MOUSE_POINTER)),

}