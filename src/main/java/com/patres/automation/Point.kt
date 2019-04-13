package com.patres.automation


import com.patres.automation.excpetion.PointVectorFormatException
import java.awt.MouseInfo

class Point(
        var x: Int,
        var y: Int,
        var vector: Boolean = false
) {

    override fun toString(): String {
        return String.format("(%d;%d)", x, y)
    }

    val xTransform: Int
        get() = MouseInfo.getPointerInfo().location.x + x

    val yTransform: Int
        get() = MouseInfo.getPointerInfo().location.y + y

    val xPositionPointVector: Int
        get() = if (vector) xTransform else x

    val yPositionPointVector: Int
        get() = if (vector) yTransform else y

    companion object {
        const val VECTOR_CHAR = "V"
        fun stringToPoint(pointString: String): Point {
            try {
                var point = pointString.replace("\\s".toRegex(), "")
                val vector = point.startsWith(VECTOR_CHAR)
                point = point.removePrefix(VECTOR_CHAR)
                val pointArray = point.substring(1, point.length - 1).split(";".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                val xInt = pointArray[0].toInt()
                val yInt = pointArray[1].toInt()
                return Point(xInt, yInt, vector)
            } catch (e: Exception) {
                throw PointVectorFormatException(pointString)
            }
        }

        fun transformPoint(point1: Point, point2: Point): Point {
            return Point(point1.x - point2.x, point1.y - point2.y)
        }

    }
}
