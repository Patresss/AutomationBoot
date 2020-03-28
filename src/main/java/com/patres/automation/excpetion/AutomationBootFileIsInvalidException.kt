package com.patres.automation.excpetion

import com.patres.automation.settings.LanguageManager

class AutomationBootFileIsInvalidException(file: String) : ApplicationException(LanguageManager.getLanguageString("error.invalidAutomationBootFile.parameter", file))
