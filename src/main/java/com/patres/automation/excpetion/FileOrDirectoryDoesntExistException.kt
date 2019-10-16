package com.patres.automation.excpetion

import com.patres.automation.Main
import java.text.MessageFormat

class FileOrDirectoryDoesntExistException(file: String) : ApplicationException(MessageFormat.format(Main.getLanguageString("error.fileOrDirectoryDoesntExist.parameter"), file))
