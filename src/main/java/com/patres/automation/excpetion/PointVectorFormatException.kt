package com.patres.automation.excpetion

import com.patres.automation.Main
import java.text.MessageFormat

class PointVectorFormatException(point: String) : ApplicationException(MessageFormat.format(Main.bundle.getString("error.mustBePointOrVector.parameter"), point))
