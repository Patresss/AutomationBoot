package com.patres.automation.gui.controller.pointer


import java.awt.Rectangle
import java.lang.Math.abs
import java.lang.Math.min

class RectangleFromPoints(
        private val startX: Int,
        private val startY: Int,
        private val endX: Int,
        private val endY: Int
) {

    val positionX: Int
        get() {
            return min(startX, endX)
        }

    val positionY: Int
        get() {
            return min(startY, endY)
        }

    val width: Int
        get() {
            return abs(startX - endX)
        }

    val height: Int
        get() {
            return abs(startY - endY)
        }

    val positionXAsDouble: Double
        get() {
            return positionX.toDouble()
        }

    val positionYAsDouble: Double
        get() {
            return positionY.toDouble()
        }

    val widthAsDouble: Double
        get() {
            return width.toDouble()
        }

    val heightAsDouble: Double
        get() {
            return height.toDouble()
        }

    fun calculateAwtRectangle() = Rectangle(positionX, positionY, width, height)
}