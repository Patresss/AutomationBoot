package com.patres.automation.excpetion

import com.patres.automation.settings.LanguageManager

class CannotParseParametersException(key: String) : ApplicationException(LanguageManager.getLanguageString("error.cannotCannotParseParametersException.parameter", key))
