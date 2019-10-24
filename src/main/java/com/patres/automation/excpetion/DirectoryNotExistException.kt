package com.patres.automation.excpetion

import com.patres.automation.Main
import java.text.MessageFormat

class DirectoryNotExistException(file: String) : ApplicationException(MessageFormat.format(Main.getLanguageString("error.directoryDoesntExist.parameter"), file))
