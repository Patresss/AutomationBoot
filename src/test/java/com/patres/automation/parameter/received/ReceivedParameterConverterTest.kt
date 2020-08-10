package com.patres.automation.parameter.received

import com.patres.automation.excpetion.CannotFindParameterException
import com.patres.automation.parameter.received.ReceivedParameterConverter
import com.patres.automation.parameter.sent.SentParameter
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

internal class ReceivedParameterConverterTest : FreeSpec({

    "Should convert parameters" - {
        // given
        val parameters = setOf(
                SentParameter("parameter1", "myValue1"),
                SentParameter("parameter2", "myValue2"))
        val parameterConverter = ReceivedParameterConverter("Test {{parameter1}} and the same {{parameter1}} and {{parameter2}}", parameters)

        // when
        val textWithValues = parameterConverter.replaceValue()

        // then
        textWithValues shouldBe "Test myValue1 and the same myValue1 and myValue2"
    }

    "Should convert parameters with default value" - {
        // given
        val parameters = emptySet<SentParameter>()
        val parameterConverter = ReceivedParameterConverter("Test {{parameter1=myDefaultValue1}} and the same {{parameter1=myDefaultValue1}} and {{parameter2=myDefaultValue2}}", parameters)

        // when
        val textWithValues = parameterConverter.replaceValue()

        // then
        textWithValues shouldBe "Test myDefaultValue1 and the same myDefaultValue1 and myDefaultValue2"
    }

    "Should throw exception when parameter not found" - {
        // given
        val parameters = emptySet<SentParameter>()
        val parameterConverter = ReceivedParameterConverter("Test {{parameter1}} and the same {{parameter1}} and {{parameter2}}", parameters)

        shouldThrow<CannotFindParameterException> {
            // when
            parameterConverter.replaceValue()
        }
    }

})