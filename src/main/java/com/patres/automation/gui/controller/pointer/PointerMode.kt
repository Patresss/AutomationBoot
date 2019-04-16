package com.patres.automation.gui.controller.pointer

import javafx.scene.input.MouseButton
import javafx.scene.input.MouseEvent
import javafx.scene.paint.Color

enum class PointerMode(private val mouseButton: MouseButton, val color: Color) {
    POINT(MouseButton.PRIMARY, Color.YELLOW),
    VECTOR(MouseButton.SECONDARY, Color.AQUA),
    IMAGE(MouseButton.MIDDLE, Color.GOLD);


    fun isMode(pointerMode: PointerMode, event: MouseEvent) = pointerMode === this && mouseButton === event.button

    fun isValidMode(event: MouseEvent) = mouseButton === event.button
}