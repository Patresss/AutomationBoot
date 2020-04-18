package com.patres.automation.mapper.model

import com.patres.automation.action.keyboard.PressKeyboardButtonAction
import com.patres.automation.helpers.JfxSerializedSpec
import com.patres.automation.helpers.shouldBeInstanceOfAndCheck
import com.patres.automation.helpers.shouldNotBeNullAndCheck
import com.patres.automation.keyboard.KeyboardKey
import com.patres.automation.mapper.AutomationMapper
import com.patres.automation.mapper.RootSchemaGroupMapper
import com.patres.automation.type.ActionBootKeyboard
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import java.io.File

class KeyboardFieldActionSerializedTest : JfxSerializedSpec({

    val actionDirectory = "/actions/KeyboardFieldAction"
    val keysFromFile: List<KeyboardKey> = listOf(KeyboardKey.SHIFT, KeyboardKey.A)


    "Should map file to serialized" - {
        ActionBootKeyboard.PRESS_KEYBOARD_BUTTON.name - {
            // given
            val filePath = "$actionDirectory/${ActionBootKeyboard.PRESS_KEYBOARD_BUTTON.name}.ab"

            // when
            val textFromFile = loadTextFromFile(filePath)
            val rootGroupSerialized: RootSchemaGroupSerialized = AutomationMapper.toObject(textFromFile)
            val serializedToModel = RootSchemaGroupMapper.serializedToModel(rootGroupSerialized, File("/"))

            // then
            serializedToModel.schemaGroupModel.shouldNotBeNullAndCheck {
                actions shouldHaveSize 1
                actions[0].shouldBeInstanceOfAndCheck<PressKeyboardButtonAction> {
                    actionBootType shouldBe ActionBootKeyboard.PRESS_KEYBOARD_BUTTON
                    keyboardKeys shouldBe keysFromFile

                }
            }
        }
    }

    "Should map file to serialized to file" - {
        ActionBootKeyboard.PRESS_KEYBOARD_BUTTON.name - {
            val filePath = "$actionDirectory/${ActionBootKeyboard.PRESS_KEYBOARD_BUTTON.name}.ab"
            testMapFileToSerializedToFile(filePath)
        }
    }


})