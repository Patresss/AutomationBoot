package com.patres.languagepopup


import com.patres.languagepopup.excpetion.PointFormatException

class Point(
        var x: Int,
        var y: Int
) {

    override fun toString(): String {
        return String.format("(%d;%d)", x, y)
    }

    companion object {
        fun stringToPoint(pointString: String): Point {
            try {
                val pointArray = pointString.substring(1, pointString.length - 1).split(";".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                val xInt = pointArray[0].toInt()
                val yInt = pointArray[1].toInt()
                return Point(xInt, yInt)
            } catch (e: Exception) {
                throw PointFormatException(pointString)
            }
        }
    }
}
