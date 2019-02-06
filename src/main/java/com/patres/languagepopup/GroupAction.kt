package com.patres.languagepopup

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView
import javafx.scene.Node

enum class GroupAction(
        val graphic: Node?,
        val bundleName: String = ""
){

   LEFT_MOUSE_BUTTON(FontAwesomeIconView(FontAwesomeIcon.MOUSE_POINTER), "robot.action.mouseClick"),
   MIDDLE_MOUSE_BUTTON(FontAwesomeIconView(FontAwesomeIcon.MOUSE_POINTER), "robot.action.mouseClick"),
   RIGHT_MOUSE_BUTTON(FontAwesomeIconView(FontAwesomeIcon.MOUSE_POINTER), "robot.action.mouseClick"),

}