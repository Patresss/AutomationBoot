package com.patres.automation.excpetion

import com.patres.automation.settings.LanguageManager

class PositiveIntegerFormatException(integer: String) : ApplicationException(LanguageManager.getLanguageString("error.mustBePositiveNumber.parameter", integer))