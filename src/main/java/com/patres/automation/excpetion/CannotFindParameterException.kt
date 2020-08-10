package com.patres.automation.excpetion

import com.patres.automation.settings.LanguageManager

class CannotFindParameterException(key: String) : ApplicationException(LanguageManager.getLanguageString("error.cannotFindParameterException.parameter", key))
