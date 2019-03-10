package com.patres.automation.excpetion

class FileNotExistException(file: String) : ApplicationException("File $file does not exist")
