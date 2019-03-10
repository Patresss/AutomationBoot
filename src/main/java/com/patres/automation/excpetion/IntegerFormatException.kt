package com.patres.automation.excpetion

import com.patres.automation.Main
import java.text.MessageFormat

class IntegerFormatException(integer: String) : ApplicationException(MessageFormat.format(Main.bundle.getString("error.mustBeNumber.parameter"), integer))