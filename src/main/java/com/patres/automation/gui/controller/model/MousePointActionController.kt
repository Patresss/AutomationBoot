package com.patres.automation.gui.controller.model

import com.jfoenix.controls.JFXButton
import com.patres.automation.Main
import com.patres.automation.action.mouse.MouseAction
import com.patres.automation.gui.controller.pointer.PointerController
import com.patres.automation.util.startTiming
import javafx.embed.swing.SwingFXUtils
import javafx.fxml.FXML
import javafx.scene.Node
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.control.Tooltip
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.stage.Stage
import javafx.stage.StageStyle
import java.io.ByteArrayOutputStream
import java.io.InputStream
import jdk.nashorn.tools.ShellFunctions.input
import java.io.ByteArrayInputStream
import java.io.File
import javax.imageio.ImageIO
import java.io.FileOutputStream




class MousePointActionController(
        model: MouseAction,
        fxmlFile: String = "MousePointAction.fxml"
) : TextFieldActionController(model, fxmlFile) {

    @FXML
    lateinit var pointButton: JFXButton

    @FXML
    lateinit var imageView: ImageView

    @FXML
    lateinit var labelImage: Label

    private val tooltip = Tooltip().apply {
        styleClass.add("tooltip-pointer-tooltip")
        startTiming(0.0)
    }

    var image: Image? = null
    var imageByteArrayOutputStream: ByteArrayOutputStream? = null

    override fun getNodesToSelect(): List<Node> = super.getNodesToSelect() + listOf(pointButton)

    @FXML
    override fun initialize() {
        super.initialize()
        setHandler()
        labelImage.isVisible = false
        imageView.fitWidthProperty().bind(valueText.widthProperty())
        imageView.fitHeightProperty().bind(valueText.heightProperty())
    }

    fun setImage(imageInputStream: InputStream) {
        setByteArrayOutputStream(imageInputStream)
        imageByteArrayOutputStream?.let {
            val inputStream =  ByteArrayInputStream(calculateImageBytesArray())
            image = Image(inputStream)
            imageView.image = image
            labelImage.isVisible = true
            valueText.isVisible = false
            tooltip.graphic = ImageView(image)

            val outStream = FileOutputStream(File("P:\\Programowanie\\Projekty\\Mouse and keyboard automat\\tmp\\atett.bmp"))
            outStream.write(calculateImageBytesArray())
            labelImage.tooltip = tooltip

        }
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
        imageView.isVisible = false
        valueText.isVisible = true
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
