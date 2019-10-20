package com.patres.automation.gui.controller.model

import com.jfoenix.controls.JFXButton
import com.jfoenix.controls.JFXDecorator
import com.jfoenix.controls.JFXSlider
import com.patres.automation.Main
import com.patres.automation.action.AbstractAction
import com.patres.automation.gui.controller.pointer.PointerController
import com.patres.automation.mapper.MousePointActionMapper
import com.patres.automation.mapper.model.MousePointActionSerialized
import com.patres.automation.type.ActionBootMousePoint
import com.patres.automation.util.MonitorSize
import com.patres.automation.util.fromBundle
import com.patres.automation.util.startTiming
import javafx.fxml.FXML
import javafx.scene.Node
import javafx.scene.Scene
import javafx.scene.control.ScrollPane
import javafx.scene.control.Tooltip
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.HBox
import javafx.scene.layout.StackPane
import javafx.stage.Stage
import javafx.stage.StageStyle
import javafx.util.StringConverter
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.lang.Math.min
import kotlin.math.roundToInt

class MousePointActionController(
        action: ActionBootMousePoint
) : TextActionController<ActionBootMousePoint>("MousePointAction.fxml", action) {

    @FXML
    lateinit var pointButton: JFXButton

    @FXML
    lateinit var imageView: ImageView

    @FXML
    lateinit var zoomButton: JFXButton

    @FXML
    lateinit var imageBox: HBox

    @FXML
    lateinit var thresholdSlider: JFXSlider

    var image: Image? = null

    private var imageByteArrayOutputStream: ByteArrayOutputStream? = null

    private val percentConverter = object : StringConverter<Double>() {
        override fun toString(double: Double?): String? {
            return double?.roundToInt().toString() + "%"
        }

        override fun fromString(string: String): Double? {
            return string.replace("%", "").toDouble()
        }
    }

    override fun getNodesToSelect(): List<Node> = super.getNodesToSelect() + listOf(pointButton, imageBox, imageView, zoomButton, thresholdSlider)

    @FXML
    override fun initialize() {
        super.initialize()
        setHandler()
        imageView.fitHeightProperty().bind(valueText.heightProperty())
        valueText.widthProperty().addListener { _, _, newValue -> imageView.fitWidth = newValue.toDouble() - zoomButton.width }

        setupVisible(true)

        thresholdSlider.tooltip = Tooltip(fromBundle("menu.percentMatch")).apply { startTiming(500.0) }
        thresholdSlider.valueChangingProperty().addListener { _, _, _ -> (root?.changeDetect()) }

        thresholdSlider.labelFormatter = percentConverter
    }

    override fun toModel(): AbstractAction {
        action.validation?.check(value)
        return MousePointActionMapper.controllerToModel(this)
    }

    override fun toSerialized(): MousePointActionSerialized {
        return MousePointActionMapper.controllerToSerialized(this)
    }

    fun setImage(imageInputStream: InputStream) {
        setByteArrayOutputStream(imageInputStream)
        setupVisible(false)
        imageByteArrayOutputStream?.let {
            val inputStream = ByteArrayInputStream(calculateImageBytesArray())
            image = Image(inputStream)
            imageView.image = image
            zoomButton.setOnAction { createNewWindowForImage() }
        }
        root?.changeDetect()
    }

    override fun shouldCheckValidation(): Boolean {
        return value.isNotEmpty() && valueText.isVisible
    }

    private fun setupVisible(isText: Boolean) {
        imageBox.isVisible = !isText
        thresholdSlider.isVisible = !isText

        valueText.isVisible = isText

        if (!isText) {
            validLabel.isVisible = false
        }
    }

    private fun createNewWindowForImage() {
        val root = StackPane()
        val scrollPane = ScrollPane().apply { styleClass.add("pane-with-primary-background") }
        scrollPane.content = ImageView(image)
        root.children.add(scrollPane)

        val stage = Stage()
        val decorator = JFXDecorator(stage, root, false, true, true)

        val width = min((image?.width ?: 500.0) + Main.sceneBarWeight, MonitorSize.width)
        val height = min((image?.height
                ?: 500.0) + Main.sceneBarHeight + 8.0, MonitorSize.height) // add 8.0 because of scroll
        val scene = Scene(decorator, width, height)

        Main.setStyle(scene)
        stage.titleProperty().bind(Main.createStringBinding("application.name"))
        stage.scene = scene

        stage.show()
    }


    private fun setByteArrayOutputStream(inputStream: InputStream) {
        imageByteArrayOutputStream = ByteArrayOutputStream()
        imageByteArrayOutputStream?.let { imageByteArrayOutputStream ->
            val buffer = ByteArray(1024)
            var len: Int = inputStream.read(buffer)
            while (len > -1) {
                imageByteArrayOutputStream.write(buffer, 0, len)
                len = inputStream.read(buffer)
            }
            imageByteArrayOutputStream.flush()
        }
    }

    fun calculateImageBytesArray() = imageByteArrayOutputStream?.toByteArray()

    fun setText(text: String) {
        valueText.text = text

        image = null
        imageByteArrayOutputStream = null
        setupVisible(true)
        root?.changeDetect()
    }

    private fun setHandler() {
        pointButton.setOnAction {
            Main.mainStage.isIconified = true
            showPointerStage()
        }
    }

    private fun showPointerStage() {
        val stage = Stage()
        stage.initStyle(StageStyle.TRANSPARENT)
        stage.title = "Set point"
        stage.scene = loadPointScene(stage)
        stage.show()
    }

    private fun loadPointScene(stage: Stage): Scene {
        val pointController = PointerController(stage, this)
        return pointController.scene
    }

}
