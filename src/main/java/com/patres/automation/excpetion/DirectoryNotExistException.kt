package com.patres.automation.excpetion

import com.patres.automation.settings.LanguageManager
import java.text.MessageFormat

class DirectoryNotExistException(file: String) : ApplicationException(LanguageManager.getLanguageString("error.directoryDoesntExist.parameter", file))
