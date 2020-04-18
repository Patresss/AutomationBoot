package com.patres.automation.mapper

import com.patres.automation.action.keyboard.HoldKeyboardButtonAction
import com.patres.automation.action.keyboard.PressKeyboardButtonAction
import com.patres.automation.action.keyboard.ReleaseKeyboardButtonAction
import com.patres.automation.gui.controller.model.KeyboardButtonActionController
import com.patres.automation.helpers.JfxSpec
import com.patres.automation.helpers.shouldBeInstanceOfAndCheck
import com.patres.automation.helpers.shouldNotBeNullAndCheck
import com.patres.automation.keyboard.KeyboardKey
import com.patres.automation.mapper.model.KeyboardFieldActionSerialized
import com.patres.automation.type.ActionBootKeyboard
import io.kotest.matchers.shouldBe


class KeyboardFieldActionMapperTest : JfxSpec({

    val mappableActions = listOf(ActionBootKeyboard.PRESS_KEYBOARD_BUTTON, ActionBootKeyboard.HOLD_KEYBOARD_BUTTON, ActionBootKeyboard.RELEASE_KEYBOARD_BUTTON)
    val testedKeys: List<KeyboardKey> = listOf(KeyboardKey.SHIFT, KeyboardKey.A)

    "Should map serialized to controller" - {
        mappableActions.map { verifiedAction: ActionBootKeyboard ->
            verifiedAction.name {
                // given
                val serializedModel = KeyboardFieldActionSerialized(verifiedAction, testedKeys)

                // when
                val controller = KeyboardFieldActionMapper.serializedToController(serializedModel)

                // then
                controller.shouldNotBeNullAndCheck {
                    actionBoot shouldBe verifiedAction
                    keyboardField.keys shouldBe testedKeys
                }
            }
        }
    }

    "Should map serialized to model" - {
        ActionBootKeyboard.PRESS_KEYBOARD_BUTTON.name - {
            // given
            val serializedModel = KeyboardFieldActionSerialized(ActionBootKeyboard.PRESS_KEYBOARD_BUTTON, testedKeys)

            // when
            val model = KeyboardFieldActionMapper.serializedToModel(serializedModel, null)

            // then
            model.shouldBeInstanceOfAndCheck<PressKeyboardButtonAction> {
                actionBootType shouldBe ActionBootKeyboard.PRESS_KEYBOARD_BUTTON
                keyboardKeys shouldBe testedKeys
            }
        }
        ActionBootKeyboard.HOLD_KEYBOARD_BUTTON.name - {
            // given
            val serializedModel = KeyboardFieldActionSerialized(ActionBootKeyboard.HOLD_KEYBOARD_BUTTON, testedKeys)

            // when
            val model = KeyboardFieldActionMapper.serializedToModel(serializedModel, null)

            // then
            model.shouldBeInstanceOfAndCheck<HoldKeyboardButtonAction> {
                actionBootType shouldBe ActionBootKeyboard.HOLD_KEYBOARD_BUTTON
                keyboardKeys shouldBe testedKeys
            }
        }
        ActionBootKeyboard.RELEASE_KEYBOARD_BUTTON.name - {
            // given
            val serializedModel = KeyboardFieldActionSerialized(ActionBootKeyboard.RELEASE_KEYBOARD_BUTTON, testedKeys)

            // when
            val model = KeyboardFieldActionMapper.serializedToModel(serializedModel, null)

            // then
            model.shouldBeInstanceOfAndCheck<ReleaseKeyboardButtonAction> {
                actionBootType shouldBe ActionBootKeyboard.RELEASE_KEYBOARD_BUTTON
                keyboardKeys shouldBe testedKeys
            }
        }
    }

    "Should map controller to serialized" - {
        mappableActions.map { verifiedAction: ActionBootKeyboard ->
            verifiedAction.name {
                // given
                val controller = KeyboardButtonActionController(verifiedAction).apply {
                    testedKeys.forEach { keyboardField.addKeyboardButton(it) }
                }

                // when
                val serialized = KeyboardFieldActionMapper.controllerToSerialized(controller)

                // then
                serialized.shouldNotBeNullAndCheck {
                    actionBootType shouldBe verifiedAction
                    keys shouldBe keys
                }
            }
        }
    }

    "Should map controller to model" - {
        ActionBootKeyboard.PRESS_KEYBOARD_BUTTON.name - {
            // given
            val controller = KeyboardButtonActionController(ActionBootKeyboard.PRESS_KEYBOARD_BUTTON).apply {
                testedKeys.forEach { keyboardField.addKeyboardButton(it) }
            }

            // when
            val model = KeyboardFieldActionMapper.controllerToModel(controller)

            // then
            model.shouldBeInstanceOfAndCheck<PressKeyboardButtonAction> {
                actionBootType shouldBe ActionBootKeyboard.PRESS_KEYBOARD_BUTTON
                keyboardKeys shouldBe testedKeys
            }
        }
        ActionBootKeyboard.HOLD_KEYBOARD_BUTTON.name - {
            // given
            val controller = KeyboardButtonActionController(ActionBootKeyboard.HOLD_KEYBOARD_BUTTON).apply {
                testedKeys.forEach { keyboardField.addKeyboardButton(it) }
            }

            // when
            val model = KeyboardFieldActionMapper.controllerToModel(controller)

            // then
            model.shouldBeInstanceOfAndCheck<HoldKeyboardButtonAction> {
                actionBootType shouldBe ActionBootKeyboard.HOLD_KEYBOARD_BUTTON
                keyboardKeys shouldBe testedKeys
            }
        }
        ActionBootKeyboard.RELEASE_KEYBOARD_BUTTON.name - {
            // given
            val controller = KeyboardButtonActionController(ActionBootKeyboard.RELEASE_KEYBOARD_BUTTON).apply {
                testedKeys.forEach { keyboardField.addKeyboardButton(it) }
            }

            // when
            val model = KeyboardFieldActionMapper.controllerToModel(controller)

            // then
            model.shouldBeInstanceOfAndCheck<ReleaseKeyboardButtonAction> {
                actionBootType shouldBe ActionBootKeyboard.RELEASE_KEYBOARD_BUTTON
                keyboardKeys shouldBe testedKeys
            }
        }
    }

})
