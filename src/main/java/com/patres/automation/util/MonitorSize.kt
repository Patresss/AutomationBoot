package com.patres.automation.util

import javafx.stage.Screen

object MonitorSize {

    val width: Double
        get()  = Screen.getPrimary().visualBounds.width

    val height: Double
        get()  = Screen.getPrimary().visualBounds.height

}