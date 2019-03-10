package com.patres.automation.excpetion

import com.patres.automation.Main
import java.text.MessageFormat

class FileNotExistException(file: String) : ApplicationException(MessageFormat.format(Main.bundle.getString("error.fileDoesntExist.parameter"), file))
