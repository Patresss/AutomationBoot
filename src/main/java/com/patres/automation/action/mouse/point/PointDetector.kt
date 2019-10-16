package com.patres.automation.action.mouse.point

import com.patres.automation.point.Point

interface PointDetector {
    fun calculatePoint(): Point?
}