package com.patres.automation.parameter.sent

data class SentParameter(
        val key: String,
        val value: String
) {

    companion object {
        const val KEY_VALUE_DELIMITER = "="
    }

    override fun toString() = "$key$KEY_VALUE_DELIMITER$value}"
}