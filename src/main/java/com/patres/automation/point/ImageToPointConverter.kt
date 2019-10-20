package com.patres.automation.point

import org.opencv.core.*
import org.opencv.imgcodecs.Imgcodecs
import org.opencv.imgproc.Imgproc
import org.slf4j.LoggerFactory
import java.awt.GraphicsEnvironment
import java.awt.Rectangle
import java.awt.Robot
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO
import kotlin.system.measureTimeMillis


class ImageToPointConverter(private val templateByteArray: ByteArray, private val thresholdMatch: Double = 0.9) {

    companion object {
        var robot = Robot()
        val logger = LoggerFactory.getLogger(ImageToPointConverter::class.java)!!
        const val MATCH_METHOD: Int = Imgproc.TM_CCOEFF_NORMED
    }

    fun calculatePointByTemplateMatchAndLogTime(): Point? {
        var point: Point? = null
        val measureTimeMillis = measureTimeMillis {
            point = calculatePointByTemplateMatch()
        }
        logger.info("Duration of match template: $measureTimeMillis")
        return point
    }

    private fun calculatePointByTemplateMatch(): Point? {
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