package com.patres.automation.excpetion

import com.patres.automation.Main
import java.text.MessageFormat

class PointFormatException(point: String) : ApplicationException(MessageFormat.format(Main.bundle.getString("error.mustBePoint.parameter"), point))
