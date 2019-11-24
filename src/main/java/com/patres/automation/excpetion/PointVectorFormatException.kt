package com.patres.automation.excpetion

import com.patres.automation.settings.LanguageManager

class PointVectorFormatException(point: String) : ApplicationException(LanguageManager.getLanguageString("error.mustBePointOrVector.parameter", point))
