package com.patres.automation.mapper.model

import com.patres.automation.action.mouse.ScrollWheelDownAction
import com.patres.automation.action.mouse.ScrollWheelUpAction
import com.patres.automation.helpers.JfxSerializedSpec
import com.patres.automation.helpers.shouldBeInstanceOfAndCheck
import com.patres.automation.helpers.shouldNotBeNullAndCheck
import com.patres.automation.mapper.AutomationMapper
import com.patres.automation.mapper.RootSchemaGroupMapper
import com.patres.automation.type.ActionBootTextField
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import java.io.File

class TextFieldActionSerializedTest : JfxSerializedSpec({

    val actionDirectory = "/actions/TextFieldAction"
    val mappableActions = listOf(ActionBootTextField.SCROLL_WHEEL_UP, ActionBootTextField.SCROLL_WHEEL_DOWN)
    val testedValueText = "4"

    "Should map file to serialized" - {
        ActionBootTextField.SCROLL_WHEEL_UP.name {
            // given
            val filePath = "$actionDirectory/${ActionBootTextField.SCROLL_WHEEL_UP.name}.ab"

            // when
            val textFromFile = loadTextFromFile(filePath)
            val rootGroupSerialized: RootSchemaGroupSerialized = AutomationMapper.toObject(textFromFile)
            val serializedToModel = RootSchemaGroupMapper.serializedToModel(rootGroupSerialized, File("/"))

            // then
            serializedToModel.schemaGroupModel.shouldNotBeNullAndCheck {
                actions shouldHaveSize 1
                actions[0].shouldBeInstanceOfAndCheck<ScrollWheelUpAction> {
                    actionBootType shouldBe ActionBootTextField.SCROLL_WHEEL_UP
                    numberOfScrolls shouldBe testedValueText.toInt()
                    down shouldBe false
                }
            }
        }
        ActionBootTextField.SCROLL_WHEEL_DOWN.name {
            // given
            val filePath = "$actionDirectory/${ActionBootTextField.SCROLL_WHEEL_DOWN.name}.ab"

            // when
            val textFromFile = loadTextFromFile(filePath)
            val rootGroupSerialized: RootSchemaGroupSerialized = AutomationMapper.toObject(textFromFile)
            val serializedToModel = RootSchemaGroupMapper.serializedToModel(rootGroupSerialized, File("/"))

            // then
            serializedToModel.schemaGroupModel.shouldNotBeNullAndCheck {
                actions shouldHaveSize 1
                actions[0].shouldBeInstanceOfAndCheck<ScrollWheelDownAction> {
                    actionBootType shouldBe ActionBootTextField.SCROLL_WHEEL_DOWN
                    numberOfScrolls shouldBe testedValueText.toInt()
                    down shouldBe true
                }
            }
        }
    }

    "Should map file to serialized to file" - {
        mappableActions.map { verifiedAction: ActionBootTextField ->
            verifiedAction.name - {
                val filePath = "$actionDirectory/${verifiedAction.name}.ab"
                testMapFileToSerializedToFile(filePath)
            }
        }
    }

})