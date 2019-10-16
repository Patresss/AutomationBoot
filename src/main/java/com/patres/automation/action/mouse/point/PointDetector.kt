package com.patres.automation.action.mouse.point

import com.patres.automation.point.Point

abstract class PointDetector {

    abstract fun calculatePoint(): Point?
}