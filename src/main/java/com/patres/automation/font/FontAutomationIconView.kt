package com.patres.automation.font

import de.jensd.fx.glyphs.GlyphIcon
import javafx.scene.text.Font

class FontAutomationIconView(
        icon: FontAutomationIcon = FontAutomationIcon.LEFT_MOUSE_BUTTON,
        iconSize: String = DEFAULT_SIZE.toString()
) : GlyphIcon<FontAutomationIcon>(FontAutomationIcon::class.java) {

    companion object {
        private const val TTF_PATH = "/font/FontAutomation.ttf"
        const val DEFAULT_SIZE = 22.0

        init {
            Font.loadFont(FontAutomationIconView::class.java.getResource(TTF_PATH).openStream(), DEFAULT_SIZE)
        }
    }

    init {
        setIcon(icon)
        style = "-fx-font-family: ${icon.fontFamily()}; -fx-font-size: $iconSize;"
    }

    override fun getDefaultGlyph(): FontAutomationIcon {
        return FontAutomationIcon.LEFT_MOUSE_BUTTON
    }

}
