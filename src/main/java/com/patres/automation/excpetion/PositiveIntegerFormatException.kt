package com.patres.automation.excpetion

import com.patres.automation.Main
import java.text.MessageFormat

class PositiveIntegerFormatException(integer: String) : ApplicationException(MessageFormat.format(Main.getLanguageString("error.mustBePositiveNumber.parameter"), integer))