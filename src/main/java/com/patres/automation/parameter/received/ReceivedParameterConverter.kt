package com.patres.automation.parameter.received

import com.patres.automation.excpetion.CannotFindParameterException
import com.patres.automation.parameter.sent.SentParameter

class ReceivedParameterConverter(val text: String, private val sentParameters: Set<SentParameter>) {

    companion object {
        val PARAMETER_PATTERN = """\{\{(?:.)*?\}\}""".toRegex()
    }

    fun replaceValue(): String {
        var replacedText = text
        val receiveParameters = extractReceiveParameters()
        receiveParameters.forEach { parameter ->
            replacedText = replacedText.replace(parameter.toString(), findValue(parameter))
        }
        return replacedText
    }

    private fun extractReceiveParameters(): Set<ReceivedParameter> {
        val matches = PARAMETER_PATTERN.findAll(text)
        return matches
                .map { ReceivedParameter.of(it.value) }
                .toSet()
    }

    private fun findValue(receivedParameter: ReceivedParameter) : String {
        return sentParameters
                .find { sendParameter -> receivedParameter.key == sendParameter.key  }
                ?.value
                ?: receivedParameter.defaultValue
                ?: throw CannotFindParameterException(receivedParameter.key)
    }
}