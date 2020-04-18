package com.patres.automation.mapper.model

import com.patres.automation.action.delay.DelayAction
import com.patres.automation.action.delay.DelayType
import com.patres.automation.helpers.JfxSerializedSpec
import com.patres.automation.helpers.shouldBeInstanceOfAndCheck
import com.patres.automation.helpers.shouldNotBeNullAndCheck
import com.patres.automation.mapper.AutomationMapper
import com.patres.automation.mapper.RootSchemaGroupMapper
import com.patres.automation.type.ActionBootDelay
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import java.io.File

class DelayActionSerializedTest : JfxSerializedSpec({

    val actionDirectory = "/actions/DelayAction"
    val valueFromFile = "123"
    val delayTypeFroFile = DelayType.SECONDS

    "Should map file to serialized" - {
        ActionBootDelay.values().map { verifiedAction: ActionBootDelay ->
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
                        delay shouldBe valueFromFile.toLong()
                        delayType shouldBe delayTypeFroFile
                    }
                }
            }
        }
    }

    "Should map file to serialized to file" - {
        ActionBootDelay.values().map { verifiedAction: ActionBootDelay ->
            verifiedAction.name - {
                val filePath = "$actionDirectory/${verifiedAction.name}.ab"
                testMapFileToSerializedToFile(filePath)
            }
        }
    }

})