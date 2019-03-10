package com.patres.automation.gui.controller

import com.patres.automation.Main
import com.patres.automation.Point
import com.patres.automation.gui.controller.model.MousePointActionController
import javafx.animation.FadeTransition
import javafx.animation.ScaleTransition
import javafx.event.EventHandler
import javafx.geometry.Rectangle2D
import javafx.scene.Cursor
import javafx.scene.Scene
import javafx.scene.input.MouseEvent
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.scene.shape.Circle
import javafx.stage.Screen
import javafx.stage.Stage
import javafx.util.Duration

class PointerController(private val stage: Stage, private val pointPane: MousePointActionController) {

    companion object {
        private const val CIRCLE_OPACITY = 0.6
    }

    lateinit var scene: Scene
    private val pane: Pane = Pane()
    private var circlePoint: Circle? = null


    init {
        setScene()
        setStyle()
        loadCirclePoint()
        addMouseListener()
    }

    private fun setScene() {
        val primaryScreenBounds = Screen.getPrimary().visualBounds
        val width = primaryScreenBounds.width
        val height = primaryScreenBounds.height
        this.scene = Scene(pane, width, height)
    }

    private fun setStyle() {
        scene.fill = Color.TRANSPARENT
        scene.stylesheets.add(Main.getStylesheet())
        pane.styleClass.add("stackpane-background")
        pane.cursor = Cursor.CROSSHAIR
    }

    private fun loadCirclePoint() {
        this.circlePoint = Circle(12.0)
        circlePoint!!.fill = Color.YELLOW
        circlePoint!!.opacity = 0.0
        pane.children.add(circlePoint)
    }

    private fun addMouseListener() {
        pane.setOnMousePressed { pressedEvent ->
            loadPressedHandler(pressedEvent)
            pane.onMouseDragged = EventHandler<MouseEvent> { this.loadDraggedHandler(it) }
            pane.onMouseReleased = EventHandler<MouseEvent> { this.loadReleasedHandler(it) }
        }
    }

    private fun loadPressedHandler(pressedEvent: MouseEvent) {
        val fadeStart = FadeTransition(Duration.millis(200.0), circlePoint)
        fadeStart.fromValue = 0.0
        fadeStart.toValue = CIRCLE_OPACITY
        fadeStart.play()

        circlePoint!!.centerX = pressedEvent.screenX - stage.x
        circlePoint!!.centerY = pressedEvent.screenY - stage.y
    }

    private fun loadDraggedHandler(draggedEvent: MouseEvent) {
        circlePoint!!.centerX = draggedEvent.screenX - stage.x
        circlePoint!!.centerY = draggedEvent.screenY - stage.y
    }

    private fun loadReleasedHandler(releasedEvent: MouseEvent) {
        val scaleEnd = ScaleTransition(Duration.millis(200.0), circlePoint)
        scaleEnd.toX = 0.0
        scaleEnd.toY = 0.0
        scaleEnd.setOnFinished {
            val x = (releasedEvent.screenX - stage.x).toInt()
            val y = (releasedEvent.screenY - stage.y).toInt()

            val point = Point(x, y)
            pointPane.valueText.text = point.toString()
            stage.close()
            Main.mainStage.isIconified = false
        }

        scaleEnd.play()
    }

}
