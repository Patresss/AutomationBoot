package com.patres.automation.point

import javafx.scene.image.Image
import javafx.scene.image.PixelReader
import org.slf4j.LoggerFactory
import java.awt.GraphicsEnvironment
import java.awt.Rectangle
import java.awt.Robot
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO


class ImageToPointConverter(private val image: Image) {

    companion object {
        var robot = Robot()
        val logger = LoggerFactory.getLogger(ImageToPointConverter::class.java)!!
    }

    private val widthImage = image.width.toInt()
    private val heightImage = image.height.toInt()
    private val imagePixelReader = image.pixelReader!!

    fun calculatePointInTheMiddle(): Point? {
        val screenShoot = calculateImageFromAllMonitors()

        val widthScreenShoot = (screenShoot.width - image.width).toInt()
        val heightScreenShot = (screenShoot.height - image.height).toInt()
        val pixelReader = screenShoot.pixelReader
        val point = calculatePoint(widthScreenShoot, heightScreenShot, pixelReader)

        if (point == null) {
            logger.warn("Point cannot be founded in image")
        }
        return point
    }

    private fun calculatePoint(widthScreenShoot: Int, heightScreenShot: Int, pixelReader: PixelReader): Point? {
        for (xScreenShot in 0 until widthScreenShoot) {
            screenShootLoop@ for (yScreenShot in 0 until heightScreenShot) {

                for (xImage in 0 until widthImage) {
                    for (yImage in 0 until heightImage) {
                        val colorFromScreenShot = pixelReader.getColor(xScreenShot + xImage, yScreenShot + yImage)
                        val colorFromImage = imagePixelReader.getColor(xImage, yImage)

                        if (colorFromScreenShot != colorFromImage) {
                            continue@screenShootLoop
                        }

                        if (xImage == widthImage - 1 && yImage == heightImage - 1) {
                            val xCenter = xScreenShot + widthImage / 2
                            val yCenter = yScreenShot + heightImage / 2
                            return Point(xCenter, yCenter)
                        }
                    }
                }
            }
        }
        return null
    }

    private fun calculateImageFromAllMonitors(): Image {
        var screenRect = Rectangle(0, 0, 0, 0)
        for (gd in GraphicsEnvironment.getLocalGraphicsEnvironment().screenDevices) {
            screenRect = screenRect.union(gd.defaultConfiguration.bounds)
        }
        val capture = Robot().createScreenCapture(screenRect)
        val os = ByteArrayOutputStream()
        ImageIO.write(capture, "bmp", os)
        val inputStream = ByteArrayInputStream(os.toByteArray())
        return Image(inputStream)
    }

}