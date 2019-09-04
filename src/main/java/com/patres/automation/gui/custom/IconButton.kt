package com.patres.automation.gui.custom

import com.jfoenix.controls.JFXButton
import com.patres.automation.util.getIcon
import de.jensd.fx.glyphs.GlyphIcons
import javafx.animation.Interpolator
import javafx.animation.ScaleTransition
import javafx.beans.property.SimpleDoubleProperty
import javafx.util.Duration

class IconButton(glyphIcons: GlyphIcons) : JFXButton() {

    companion object {
        val expandToMaxProperty = SimpleDoubleProperty(1.1)
    }

    init {
        styleClass.add("animated-option-button")
        graphic = glyphIcons.getIcon()
        initZoomIcon()
    }

    private fun initZoomIcon() {
        val scaleTransition = ScaleTransition(Duration.millis(80.0), this).apply {
            cycleCount = 1
            interpolator = Interpolator.EASE_BOTH
        }

        setOnMouseEntered {
            scaleTransition.fromX = scaleX
            scaleTransition.fromY = scaleY
            scaleTransition.toX = expandToMaxProperty.get()
            scaleTransition.toY = expandToMaxProperty.get()
            scaleTransition.playFromStart()
        }

        setOnMouseExited {
            scaleTransition.fromX = scaleX
            scaleTransition.fromY = scaleY
            scaleTransition.toX = 1.0
            scaleTransition.toY = 1.0
            scaleTransition.playFromStart()
        }
    }

}