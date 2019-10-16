package com.patres.automation.validation

import com.patres.automation.excpetion.PointVectorFormatException

class PointVectorValidation : Validationable() {

    companion object {
        private const val pointPattern = "\\((\\d+);(\\d+)\\)" // (12;34)
        private const val vectorPattern = "V?\\((-?\\d+);(-?\\d+)\\)" // V(-12;34)
    }

    override fun isValid(value: String): Boolean {
        return value.matches(pointPattern.toRegex()) || value.matches(vectorPattern.toRegex())
    }

    override fun throwException(value: String) {
        throw PointVectorFormatException(value)
    }

    override fun getErrorMessageProperty() = "error.mustBePointOrVector"


}

