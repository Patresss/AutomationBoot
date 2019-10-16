package com.patres.automation.action.mouse.point

import com.patres.automation.point.Point

class SpecificPointDetector(val point: Point) : PointDetector() {

    override fun calculatePoint() = point

}