package com.patres.automation.action.mouse.point

import com.patres.automation.point.ImageToPointConverter
import com.patres.automation.point.Point
import javafx.beans.property.BooleanProperty

class ImagePointDetector(
        val templateByteArray: ByteArray,
        val threshold: Double,
        val ignoreIfNotFound: Boolean,
        val automationRunningProperty: BooleanProperty
) : PointDetector {

    private val converter = ImageToPointConverter(templateByteArray, threshold / 100.0)

    override fun calculatePoint(): Point? {
        var point = loadPoint()
        if (!ignoreIfNotFound) {
            while (point == null && automationRunningProperty.get()) {
                point = loadPoint()
            }
        }
        return point
    }

    private fun loadPoint() = converter.calculatePointByTemplateMatchAndLogTime()

    override fun toString() = "ImagePointDetector(templateByteArray=${templateByteArray}, threshold=$threshold, ignoreIfNotFound=$ignoreIfNotFound)"

}