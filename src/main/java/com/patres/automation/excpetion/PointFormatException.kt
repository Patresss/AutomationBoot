package com.patres.automation.excpetion

class PointFormatException(point: String) : ApplicationException("Please enter the point \"$point\" in (x;y) format")
