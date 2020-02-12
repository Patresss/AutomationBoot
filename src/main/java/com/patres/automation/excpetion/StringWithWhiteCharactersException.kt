package com.patres.automation.excpetion

import com.patres.automation.settings.LanguageManager

class StringWithWhiteCharactersException(value: String) : ApplicationException(LanguageManager.getLanguageString("error.stringWithWhiteCharacters.parameter", value))