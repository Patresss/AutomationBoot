package com.patres.automation.menuItem

import afester.javafx.svg.SvgLoader
import com.jfoenix.svg.SVGGlyph
import com.jfoenix.svg.SVGGlyphLoader
import com.patres.automation.Main
import javafx.scene.Group
import javafx.scene.Node
import javafx.scene.layout.Region
import javafx.scene.shape.SVGPath


enum class CustomIcon(val svgName: String) {

    MOUSE_LEFT_CLICK("m.svg");

    val icon = loadIcon()

    fun loadIcon(): Node {
        val svgFile = javaClass.getResourceAsStream("/icon/$svgName")
        val loader = SvgLoader()
        val svg = loader.loadSvg(svgFile)

        val REQUIRED_WIDTH = 20.0
        val REQUIRED_HEIGHT = 25.0

        resize(svg, REQUIRED_WIDTH, REQUIRED_HEIGHT);
        return svg
    }

    private fun resize(svg: Group, width: Double, height: Double) {
        val originalWidth = svg.prefWidth(-1.0)
        val originalHeight = svg.prefHeight(originalWidth)

        val scaleX = width / originalWidth
        val scaleY = height / originalHeight

        svg.scaleX = scaleX
        svg.scaleY = scaleY
    }
}