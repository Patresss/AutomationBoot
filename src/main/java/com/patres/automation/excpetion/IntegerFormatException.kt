package com.patres.automation.excpetion

import com.patres.automation.settings.LanguageManager

class IntegerFormatException(integer: String) : ApplicationException(LanguageManager.getLanguageString("error.mustBeNumber.parameter", integer))