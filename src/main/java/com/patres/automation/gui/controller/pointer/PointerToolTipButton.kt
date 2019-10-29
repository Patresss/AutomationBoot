package com.patres.automation.gui.controller.pointer

import com.jfoenix.controls.JFXButton
import com.patres.automation.util.fromBundle
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView
import javafx.scene.control.Label
import javafx.scene.layout.VBox

class PointerToolTipButton {

    companion object {
        private val QUESTION_ICON = FontAwesomeIconView(FontAwesomeIcon.QUESTION)
        private const val QUESTION_ICON_PADDING = 50.0
    }

    val button: JFXButton
    val stackPane: VBox

    init {
        button = JFXButton(null, QUESTION_ICON)
        stackPane = VBox().apply {
            translateX = QUESTION_ICON_PADDING
            translateY = QUESTION_ICON_PADDING
            isVisible = false
            styleClass.add("tooltip-pointer-pane")
            hoverProperty().addListener { _, _, newValue ->
                isVisible = newValue
                button.isVisible = !newValue
            }
        }

        button.apply {
            translateX = QUESTION_ICON_PADDING
            translateY = QUESTION_ICON_PADDING
            styleClass.add("tooltip-pointer-button")
            graphic.styleClass.add("tooltip-pointer-icon")
            hoverProperty().addListener { _, _, newValue ->
                stackPane.isVisible = newValue
            }
        }

        val header = Label(fromBundle("pointer.tooltiop.header")).apply { styleClass.add("tooltip-pointer-label-header") }
        stackPane.children.add(header)
        stackPane.children.add(Label(fromBundle("pointer.tooltiop.point")).apply { styleClass.add("tooltip-pointer-label") })
        stackPane.children.add(Label(fromBundle("pointer.tooltiop.vector")).apply { styleClass.add("tooltip-pointer-label") })
        stackPane.children.add(Label(fromBundle("pointer.tooltiop.image")).apply { styleClass.add("tooltip-pointer-label") })
    }

}