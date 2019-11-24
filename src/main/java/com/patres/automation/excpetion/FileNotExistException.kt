package com.patres.automation.excpetion

import com.patres.automation.settings.LanguageManager

class FileNotExistException(file: String) : ApplicationException(LanguageManager.getLanguageString("error.fileDoesntExist.parameter", file))
