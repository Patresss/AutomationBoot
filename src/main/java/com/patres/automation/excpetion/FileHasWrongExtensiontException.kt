package com.patres.automation.excpetion

import com.patres.automation.Main
import java.text.MessageFormat

class FileHasWrongExtensiontException(file: String, extension: String) : ApplicationException(MessageFormat.format(Main.bundle.getString("error.fileHasWrongExtension.parameter"), file, extension))
