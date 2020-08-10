package com.patres.automation.parameter.sent

import com.patres.automation.excpetion.CannotParseParametersException
import org.slf4j.LoggerFactory

class SentParameterConverter(val text: String) {

    companion object {
        val logger = LoggerFactory.getLogger(SentParameterConverter::class.java)!!
    }

    fun calculateParameters(): Set<SentParameter> {
        try {
            if (!text.contains("?")) {
                return emptySet()
            }
            val parameterText = text.split("?")[1]
            val parameterList = parameterText.split("&")
            return parameterList
                    .map { parameterWithValue -> parameterWithValue.split(SentParameter.KEY_VALUE_DELIMITER) }
                    .map { keyValue -> SentParameter(keyValue[0], keyValue[1]) }
                    .toSet()
        } catch (e: Exception) {
            logger.error("Cannot parse text to parameters ${e.message}")
            throw CannotParseParametersException(text)
        }

    }
}