package com.patres.automation.parameter.received

class ReceivedParameter(
        val key: String,
        val defaultValue: String? = null
) {

    companion object {
        const val PREFIX = "{{"
        const val SUFFIX = "}}"
        private const val DEFAULT_VALUE_DELIMITER = "="

        fun of(stringValue: String): ReceivedParameter {
            val value = stringValue.removeSurrounding(PREFIX, SUFFIX)
            return if (value.contains(DEFAULT_VALUE_DELIMITER)) {
                val keyWithDefaultValue = value.split(DEFAULT_VALUE_DELIMITER)
                ReceivedParameter(keyWithDefaultValue[0], keyWithDefaultValue[1])
            } else {
                ReceivedParameter(value)
            }
        }
    }

    override fun toString() =
            if (defaultValue == null)
                "$PREFIX$key$SUFFIX"
            else
                "$PREFIX$key=$defaultValue$SUFFIX"

}