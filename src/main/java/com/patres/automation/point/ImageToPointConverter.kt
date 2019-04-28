package com.patres.automation.point

import javafx.scene.image.Image
import javafx.scene.image.PixelReader
import org.opencv.core.*
import org.opencv.imgcodecs.Imgcodecs
import org.opencv.imgproc.Imgproc
import org.slf4j.LoggerFactory
import java.awt.GraphicsEnvironment
import java.awt.Rectangle
import java.awt.Robot
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO
import kotlin.system.measureTimeMillis


class ImageToPointConverter(private val image: Image) {

    companion object {
        var robot = Robot()
        val logger = LoggerFactory.getLogger(ImageToPointConverter::class.java)!!
        const val MATCH_METHOD: Int = Imgproc.TM_CCOEFF_NORMED
    }

    private val widthImage = image.width.toInt()
    private val heightImage = image.height.toInt()
    private val imagePixelReader = image.pixelReader!!

    fun calculatePointByTemplateMatchAndLogTime(templateByteArray: ByteArray, thresholdMatch: Double = 0.9): Point? {
        var point: Point? = null
        val measureTimeMillis = measureTimeMillis {
            point = calculatePointByTemplateMatch(templateByteArray, thresholdMatch)
        }
        logger.info("Duration of match template: $measureTimeMillis")
        return point
    }

    private fun calculatePointByTemplateMatch(templateByteArray: ByteArray, thresholdMatch: Double = 0.9): Point? {
            val screenShoot = calculateByteArrayFromAllMonitors()
            val image = Imgcodecs.imdecode(MatOfByte(*screenShoot), Imgcodecs.CV_LOAD_IMAGE_UNCHANGED)
            val template = Imgcodecs.imdecode(MatOfByte(*templateByteArray), Imgcodecs.CV_LOAD_IMAGE_UNCHANGED)

            val resultCols = image.cols() - template.cols() + 1
            val resultRows = image.rows() - template.rows() + 1
            val result = Mat(resultRows, resultCols, CvType.CV_32FC1)

            Imgproc.matchTemplate(image, template, result, MATCH_METHOD)

            val mmr = Core.minMaxLoc(result)

            if (mmr.maxVal < thresholdMatch) {
                logger.warn("Cannot find point. Value: ${mmr.maxVal} has to be greater than thresholdMatch: $thresholdMatch")
                return null
            }
            val startPoint = mmr.maxLoc
            val endPoint = Point(startPoint.x + template.cols(), startPoint.y + template.rows())

            val xInTheMiddle = doubleArrayOf(startPoint.x, endPoint.x).average().toInt()
            val yInTheMiddle = doubleArrayOf(startPoint.y, endPoint.y).average().toInt()

            return Point(xInTheMiddle, yInTheMiddle)
    }

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

    private fun calculateByteArrayFromAllMonitors(): ByteArray {
        var screenRect = Rectangle(0, 0, 0, 0)
        for (gd in GraphicsEnvironment.getLocalGraphicsEnvironment().screenDevices) {
            screenRect = screenRect.union(gd.defaultConfiguration.bounds)
        }
        val capture = Robot().createScreenCapture(screenRect)
        val os = ByteArrayOutputStream()
        ImageIO.write(capture, "bmp", os)
        return os.toByteArray()
    }

}