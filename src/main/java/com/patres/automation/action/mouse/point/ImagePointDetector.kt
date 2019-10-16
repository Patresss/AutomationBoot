package com.patres.automation.action.mouse.point

import com.patres.automation.point.ImageToPointConverter
import com.patres.automation.point.Point

class ImagePointDetector(
        private val templateByteArray: ByteArray,
        private val threshold: Double
) : PointDetector {

    override fun calculatePoint() = loadPoint()

    private fun loadPoint(): Point? {
        val threshold = threshold / 100.0
        return ImageToPointConverter(templateByteArray, threshold).calculatePointByTemplateMatchAndLogTime()
    }

}