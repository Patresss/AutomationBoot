package com.patres.automation.excpetion

import com.patres.automation.server.ServerBoot
import com.patres.automation.settings.LanguageManager

class NumberMustBeBetweenException(integer: String)
    : ApplicationException(LanguageManager.getLanguageString("error.mustBeNumberBetween.parameter", integer, ServerBoot.MIN_PORT, ServerBoot.MAX_PORT))