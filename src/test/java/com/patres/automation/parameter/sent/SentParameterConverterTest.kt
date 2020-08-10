package com.patres.automation.parameter.sent

import com.patres.automation.excpetion.CannotParseParametersException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.collections.shouldHaveSize

internal class SentParameterConverterTest : FreeSpec({

    "Should calculate parameters" - {
        // given
        val parameterConverter = SentParameterConverter("myPathToFile.ab?player1=Aztecs&player2=Saracens")

        // when
        val parameters = parameterConverter.calculateParameters()

        // then
        parameters shouldHaveSize 2
        parameters shouldContainExactlyInAnyOrder setOf(SentParameter("player1", "Aztecs"), SentParameter("player2", "Saracens"))
    }

    "Should throw exception when cannot parse" - {
        // given
        val parameterConverter = SentParameterConverter("myPathToFile.ab?player1Aztecs&player2=Saracens")

        shouldThrow<CannotParseParametersException> {
            // when
            parameterConverter.calculateParameters()
        }
    }

})