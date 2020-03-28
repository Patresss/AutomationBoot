package com.patres.automation.excpetion

import com.patres.automation.settings.LanguageManager

class FileHasWrongExtensionException(file: String, extension: String) : ApplicationException(LanguageManager.getLanguageString("error.fileHasWrongExtension.parameter", file, extension))
