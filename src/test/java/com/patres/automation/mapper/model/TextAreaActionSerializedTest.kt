package com.patres.automation.mapper.model

import com.patres.automation.action.text.PasteTextAction
import com.patres.automation.action.text.TypeTextAction
import com.patres.automation.helpers.JfxSerializedSpec
import com.patres.automation.helpers.shouldBeInstanceOfAndCheck
import com.patres.automation.helpers.shouldNotBeNullAndCheck
import com.patres.automation.mapper.AutomationMapper
import com.patres.automation.mapper.RootSchemaGroupMapper
import com.patres.automation.type.ActionBootTextArea
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import java.io.File

class TextAreaActionSerializedTest : JfxSerializedSpec({

    val actionDirectory = "/actions/TextAreaAction"

    val testedValueText = "Test 123"

    "Should map file to serialized" - {
        ActionBootTextArea.PASTE_TEXT.name {
            // given
            val filePath = "$actionDirectory/${ActionBootTextArea.PASTE_TEXT.name}.ab"

            // when
            val textFromFile = loadTextFromFile(filePath)
            val rootGroupSerialized: RootSchemaGroupSerialized = AutomationMapper.toObject(textFromFile)
            val serializedToModel = RootSchemaGroupMapper.serializedToModel(rootGroupSerialized, File("/"))

            // then
            serializedToModel.schemaGroupModel.shouldNotBeNullAndCheck {
                actions shouldHaveSize 1
                actions[0].shouldBeInstanceOfAndCheck<PasteTextAction> {
                    actionBootType shouldBe ActionBootTextArea.PASTE_TEXT
                    getText() shouldBe testedValueText
                }
            }
        }
        ActionBootTextArea.TYPE_TEXT.name {
            // given
            val filePath = "$actionDirectory/${ActionBootTextArea.TYPE_TEXT.name}.ab"

            // when
            val textFromFile = loadTextFromFile(filePath)
            val rootGroupSerialized: RootSchemaGroupSerialized = AutomationMapper.toObject(textFromFile)
            val serializedToModel = RootSchemaGroupMapper.serializedToModel(rootGroupSerialized, File("/"))

            // then
            serializedToModel.schemaGroupModel.shouldNotBeNullAndCheck {
                actions shouldHaveSize 1
                actions[0].shouldBeInstanceOfAndCheck<TypeTextAction> {
                    actionBootType shouldBe ActionBootTextArea.TYPE_TEXT
                    getText() shouldBe testedValueText
                }
            }
        }

    }

    "Should map file to serialized to file" - {
        ActionBootTextArea.values().map { verifiedAction: ActionBootTextArea ->
            verifiedAction.name - {
                val filePath = "$actionDirectory/${verifiedAction.name}.ab"
                testMapFileToSerializedToFile(filePath)
            }
        }
    }


})