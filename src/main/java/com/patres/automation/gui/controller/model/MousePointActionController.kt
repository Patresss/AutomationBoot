package com.patres.automation.gui.controller.model

import com.jfoenix.controls.JFXButton
import com.jfoenix.controls.JFXDecorator
import com.patres.automation.Main
import com.patres.automation.action.mouse.MouseAction
import com.patres.automation.gui.controller.pointer.PointerController
import com.patres.automation.util.MonitorSize
import javafx.fxml.FXML
import javafx.scene.Node
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.ScrollPane
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.HBox
import javafx.scene.layout.StackPane
import javafx.stage.Stage
import javafx.stage.StageStyle
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.lang.Math.min
import javafx.stage.Screen.getPrimary
import javafx.scene.transform.Scale

class MousePointActionController(
        model: MouseAction,
        fxmlFile: String = "MousePointAction.fxml"
) : TextFieldActionController(model, fxmlFile) {

    @FXML
    lateinit var pointButton: JFXButton

    @FXML
    lateinit var imageView: ImageView

    @FXML
    lateinit var zoomButton: JFXButton

    @FXML
    lateinit var imageBox: HBox

    var image: Image? = null

    private var imageByteArrayOutputStream: ByteArrayOutputStream? = null

    override fun getNodesToSelect(): List<Node> = super.getNodesToSelect() + listOf(pointButton)

    @FXML
    override fun initialize() {
        super.initialize()
        setHandler()
        imageView.fitWidthProperty().bind(valueText.widthProperty())
        imageView.fitHeightProperty().bind(valueText.heightProperty())
        imageBox.isVisible = false
    }

    fun setImage(imageInputStream: InputStream) {
        setByteArrayOutputStream(imageInputStream)
        imageBox.isVisible = true
        valueText.isVisible = false

        imageByteArrayOutputStream?.let {
            val inputStream = ByteArrayInputStream(calculateImageBytesArray())
            image = Image(inputStream)
            imageView.image = image
            zoomButton.setOnAction { createNewWindowForImage() }
        }
        model?.root?.changeDetect()
    }

    private fun createNewWindowForImage() {
        val root = StackPane()
        val scrollPane = ScrollPane().apply { styleClass.add("pane-with-primary-background") }
        scrollPane.content = ImageView(image)
        root.children.add(scrollPane)

        val stage = Stage()
        val decorator = JFXDecorator(stage, root, false, true, true)

        val width = min((image?.width ?: 500.0)  + Main.sceneBarWeight, MonitorSize.width)
        val height = min((image?.height ?: 500.0) + Main.sceneBarHeight + 8.0 , MonitorSize.height) // add 8.0 because of scroll
        val scene = Scene(decorator, width, height)

        Main.setStyle(scene)
        stage.title = Main.tittle
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
        imageBox.isVisible = false
        valueText.isVisible = true
        model?.root?.changeDetect()
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
        stage.isMaximized = true
        stage.show()
    }

    private fun loadPointScene(stage: Stage): Scene {
        val pointController = PointerController(stage, this)
        return pointController.scene
    }

}
