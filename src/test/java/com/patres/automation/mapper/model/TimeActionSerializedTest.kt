package com.patres.automation.mapper.model

import com.patres.automation.action.delay.DelayAction
import com.patres.automation.action.delay.TimeType
import com.patres.automation.helpers.JfxSerializedSpec
import com.patres.automation.helpers.shouldBeInstanceOfAndCheck
import com.patres.automation.helpers.shouldNotBeNullAndCheck
import com.patres.automation.mapper.AutomationMapper
import com.patres.automation.mapper.RootSchemaGroupMapper
import com.patres.automation.type.ActionBootTime
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import java.io.File

class TimeActionSerializedTest : JfxSerializedSpec({

    val actionDirectory = "/actions/TimeAction"
    val valueFromFile = "123"
    val delayTypeFroFile = TimeType.SECONDS
    val mappableActions = listOf(ActionBootTime.DELAY)

    "Should map file to serialized" - {
        mappableActions.map { verifiedAction: ActionBootTime ->
            verifiedAction.name - {
                // given
                val filePath = "$actionDirectory/${verifiedAction.name}.ab"

                // when
                val textFromFile = loadTextFromFile(filePath)
                val rootGroupSerialized: RootSchemaGroupSerialized = AutomationMapper.toObject(textFromFile)
                val serializedToModel = RootSchemaGroupMapper.serializedToModel(rootGroupSerialized, File("/"))

                // then
                serializedToModel.schemaGroupModel.shouldNotBeNullAndCheck {
                    actions shouldHaveSize 1
                    actions[0].shouldBeInstanceOfAndCheck<DelayAction> {
                        actionBootType shouldBe verifiedAction
                        timeContainer.value shouldBe valueFromFile
                        timeContainer.type shouldBe delayTypeFroFile
                    }
                }
            }
        }
    }

    "Should map file to serialized to file" - {
        mappableActions.map { verifiedAction: ActionBootTime ->
            verifiedAction.name - {
                val filePath = "$actionDirectory/${verifiedAction.name}.ab"
                testMapFileToSerializedToFile(filePath)
            }
        }
    }

})