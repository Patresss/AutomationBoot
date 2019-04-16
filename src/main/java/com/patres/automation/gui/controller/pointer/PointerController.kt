package com.patres.automation.gui.controller.pointer

import com.jfoenix.controls.JFXButton
import com.patres.automation.Main
import com.patres.automation.point.Point
import com.patres.automation.point.Point.Companion.VECTOR_CHAR
import com.patres.automation.point.Point.Companion.transformPoint
import com.patres.automation.gui.controller.model.MousePointActionController
import com.patres.automation.util.fromBundle
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView
import javafx.animation.FadeTransition
import javafx.animation.ScaleTransition
import javafx.application.Platform
import javafx.event.EventHandler
import javafx.scene.Cursor
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.input.MouseButton
import javafx.scene.input.MouseEvent
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.scene.shape.Circle
import javafx.scene.shape.Line
import javafx.stage.Screen
import javafx.stage.Stage
import javafx.util.Duration
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.awt.Rectangle
import java.awt.Robot
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO


class PointerController(private val stage: Stage, private val pointPane: MousePointActionController) {

    companion object {
        private const val CIRCLE_OPACITY = 0.6
        private const val RADIUS = 12.0
        private val QUESTION_ICON = FontAwesomeIconView(FontAwesomeIcon.QUESTION)
        private const val QUESTION_ICON_PADDING = 50.0
        private const val CAPTURE_DELAY = 200L
    }

    lateinit var scene: Scene
    private val pane: Pane = Pane()
    private var circlePoint: Circle = Circle(RADIUS)

    private var firstMousePoint: Point? = null
    private var secondMousePoint: Point? = null
    private var line: Line? = null
    private var rectangle: javafx.scene.shape.Rectangle? = null
    private var mode: PointerMode? = null

    init {
        setScene()
        loadToolTipButton()
        setStyle()
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
        pane.children.remove(circlePoint)
        circlePoint = Circle(12.0)
        pane.apply {
            children.add(circlePoint)
            styleClass.add("tooltip-pointer")
        }
    }

    private fun loadToolTipButton() {
        val button = JFXButton(null, QUESTION_ICON)
        val stackPane = VBox().apply {
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

        pane.children.add(button)
        pane.children.add(stackPane)
    }

    private fun addMouseListener() {
        pane.setOnMousePressed { pressedEvent ->
            loadPressedHandler(pressedEvent)
            pane.onMouseDragged = EventHandler<MouseEvent> { this.loadDraggedHandler(it) }
            pane.onMouseReleased = EventHandler<MouseEvent> { this.loadReleasedHandler(it) }
        }
    }

    private fun loadPressedHandler(pressedEvent: MouseEvent) {
        if (mode == null) {
            when (pressedEvent.button) {
                MouseButton.PRIMARY -> mode = PointerMode.POINT
                MouseButton.MIDDLE -> mode = PointerMode.IMAGE
                MouseButton.SECONDARY -> mode = PointerMode.VECTOR
            }
        }

        mode?.let { mode ->
            if (mode.isValidMode(pressedEvent)) {
                loadCirclePoint()
                circlePoint.fill = mode.color
                val fadeStart = FadeTransition(Duration.millis(200.0), circlePoint)
                fadeStart.fromValue = 0.0
                fadeStart.toValue = CIRCLE_OPACITY
                fadeStart.play()

                circlePoint.centerX = pressedEvent.screenX - stage.x
                circlePoint.centerY = pressedEvent.screenY - stage.y
            }
        }

    }

    private fun loadDraggedHandler(draggedEvent: MouseEvent) {
        if (mode != null) {
            circlePoint.centerX = draggedEvent.screenX - stage.x
            circlePoint.centerY = draggedEvent.screenY - stage.y
            line?.endX = circlePoint.centerX
            line?.endY = circlePoint.centerY

            firstMousePoint?.let { firstMousePoint ->
                val rectangleFromPoints = RectangleFromPoints(firstMousePoint.x, firstMousePoint.y, circlePoint.centerX.toInt(), circlePoint.centerY.toInt())
                rectangle?.x = rectangleFromPoints.positionXAsDouble
                rectangle?.y = rectangleFromPoints.positionYAsDouble
                rectangle?.width = rectangleFromPoints.widthAsDouble
                rectangle?.height = rectangleFromPoints.heightAsDouble
            }

        }
    }

    private fun loadReleasedHandler(releasedEvent: MouseEvent) {
        mode?.let { mode ->
            val scaleEnd = when {
                PointerMode.POINT.isMode(mode, releasedEvent) -> loadScaleTransitionForPoint(releasedEvent)
                PointerMode.VECTOR.isMode(mode, releasedEvent) -> loadScaleTransitionForVector(releasedEvent)
                PointerMode.IMAGE.isMode(mode, releasedEvent) -> loadScaleTransitionForImage(releasedEvent)
                else -> null
            }
            scaleEnd?.play()
        }
    }

    private fun loadScaleTransition(): ScaleTransition {
        val scaleEnd = ScaleTransition(Duration.millis(200.0), circlePoint)
        scaleEnd.toX = 0.0
        scaleEnd.toY = 0.0
        return scaleEnd
    }

    private fun loadScaleTransitionForVector(releasedEvent: MouseEvent): ScaleTransition {
        val scaleEnd = loadScaleTransition()
        scaleEnd.setOnFinished {
            val pair = calculateTwoPoints(releasedEvent)
            if (pair != null) {
                pointPane.setText(VECTOR_CHAR + transformPoint(pair.second, pair.first))
                closeController()
            }
        }
        return scaleEnd
    }

    private fun loadScaleTransitionForPoint(releasedEvent: MouseEvent): ScaleTransition {
        val scaleEnd = loadScaleTransition()
        scaleEnd.setOnFinished {
            val x = (releasedEvent.screenX - stage.x).toInt()
            val y = (releasedEvent.screenY - stage.y).toInt()
            pointPane.setText(Point(x, y).toString())
            closeController()
        }
        return scaleEnd
    }

    private fun loadScaleTransitionForImage(releasedEvent: MouseEvent): ScaleTransition {
        val scaleEnd = loadScaleTransition()
        scaleEnd.setOnFinished {
            val pair = calculateTwoPoints(releasedEvent)
            if (pair != null) {
                val rectangleFromPoints = RectangleFromPoints(pair.first.x, pair.first.y, pair.second.x, pair.second.y)
                val screenRect = rectangleFromPoints.calculateAwtRectangle()
                stage.close()
                captureImage(screenRect)
            }
        }
        return scaleEnd
    }

    private fun captureImage(screenRect: Rectangle) {
        GlobalScope.launch {
            delay(CAPTURE_DELAY)
            val capture = Robot().createScreenCapture(screenRect)
            val os = ByteArrayOutputStream()
            ImageIO.write(capture, "bmp", os)
            val inputStream = ByteArrayInputStream(os.toByteArray())
            Platform.runLater {
                pointPane.setImage(inputStream)
                Main.mainStage.isIconified = false
            }
        }
    }

    private fun calculateTwoPoints(releasedEvent: MouseEvent): Pair<Point, Point>? {
        val x = (releasedEvent.screenX - stage.x).toInt()
        val y = (releasedEvent.screenY - stage.y).toInt()

        if (firstMousePoint == null) {
            firstMousePoint = Point(x, y)
            val firstMousePoint = Point(x, y)

            if (mode == PointerMode.VECTOR) {
                line = Line(x.toDouble(), y.toDouble(), x.toDouble(), y.toDouble()).apply {
                    stroke = mode?.color
                }
                pane.setOnMouseMoved { event ->
                    line?.endX = event.sceneX
                    line?.endY = event.sceneY
                }
                pane.children.add(line)
            }

            if (mode == PointerMode.IMAGE) {
                rectangle = javafx.scene.shape.Rectangle(x.toDouble(), y.toDouble(), 1.0, 1.0).apply {
                    stroke = mode?.color
                    fill = Color.TRANSPARENT
                }
                pane.setOnMouseMoved { event ->
                    val rectangleFromPoints = RectangleFromPoints(firstMousePoint.x, firstMousePoint.y, event.sceneX.toInt(), event.sceneY.toInt())
                    rectangle?.x = rectangleFromPoints.positionXAsDouble
                    rectangle?.y = rectangleFromPoints.positionYAsDouble
                    rectangle?.width = rectangleFromPoints.widthAsDouble
                    rectangle?.height = rectangleFromPoints.heightAsDouble
                }
                pane.children.add(rectangle)
            }

        } else {
            secondMousePoint = Point(x, y)
        }
        val firstMousePoint = firstMousePoint
        val secondMousePoint = secondMousePoint

        if (firstMousePoint != null && secondMousePoint != null) {
            return Pair(firstMousePoint, secondMousePoint)
        }
        return null
    }

    private fun closeController() {
        stage.close()
        Main.mainStage.isIconified = false
    }

}
