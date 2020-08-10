package com.patres.automation.mapper

import com.patres.automation.action.mouse.ScrollWheelDownAction
import com.patres.automation.action.mouse.ScrollWheelUpAction
import com.patres.automation.gui.controller.model.TextFieldActionController
import com.patres.automation.helpers.JfxSpec
import com.patres.automation.helpers.shouldBeInstanceOfAndCheck
import com.patres.automation.helpers.shouldNotBeNullAndCheck
import com.patres.automation.mapper.model.TextFieldActionSerialized
import com.patres.automation.type.ActionBootTextField
import io.kotest.matchers.shouldBe
import javafx.beans.property.SimpleBooleanProperty

internal class TextFieldActionMapperTest : JfxSpec({

    val mappableActions = listOf(ActionBootTextField.SCROLL_WHEEL_UP, ActionBootTextField.SCROLL_WHEEL_DOWN)
    val testedValueText = "4"

    "Should map serialized to controller" - {
        mappableActions.map { verifiedAction: ActionBootTextField ->
            verifiedAction.name {
                // given
                val serializedModel = TextFieldActionSerialized(verifiedAction, testedValueText)

                // when
                val controller = TextFieldActionMapper.serializedToController(serializedModel)

                // then
                controller.shouldNotBeNullAndCheck {
                    value shouldBe testedValueText
                }
            }
        }
    }

    "Should map serialized to model" - {
        ActionBootTextField.SCROLL_WHEEL_UP.name {
            // given
            val serializedModel = TextFieldActionSerialized(ActionBootTextField.SCROLL_WHEEL_UP, testedValueText)

            // when
            val model = TextFieldActionMapper.serializedToModel(serializedModel, SimpleBooleanProperty(false), emptySet())

            // then
            model.shouldBeInstanceOfAndCheck<ScrollWheelUpAction> {
                actionBootType shouldBe ActionBootTextField.SCROLL_WHEEL_UP
                numberOfScrolls shouldBe testedValueText.toInt()
                down shouldBe false
            }
        }
        ActionBootTextField.SCROLL_WHEEL_DOWN.name {
            // given
            val serializedModel = TextFieldActionSerialized(ActionBootTextField.SCROLL_WHEEL_DOWN, testedValueText)

            // when
            val model = TextFieldActionMapper.serializedToModel(serializedModel, SimpleBooleanProperty(false), emptySet())

            // then
            model.shouldBeInstanceOfAndCheck<ScrollWheelDownAction> {
                actionBootType shouldBe ActionBootTextField.SCROLL_WHEEL_DOWN
                numberOfScrolls shouldBe testedValueText.toInt()
                down shouldBe true
            }
        }
    }
    "Should map controller to serialized" - {
        ActionBootTextField.SCROLL_WHEEL_UP.name - {
            // given
            val controller = TextFieldActionController(ActionBootTextField.SCROLL_WHEEL_UP).apply {
                value = testedValueText
            }

            // when
            val serialized = TextFieldActionMapper.controllerToSerialized(controller)

            // then
            serialized.shouldNotBeNullAndCheck {
                actionBootType shouldBe ActionBootTextField.SCROLL_WHEEL_UP
                value shouldBe testedValueText
            }
        }

    }
    "Should map controller to model" - {
        ActionBootTextField.SCROLL_WHEEL_UP.name - {
            // given
            val controller = TextFieldActionController(ActionBootTextField.SCROLL_WHEEL_UP).apply {
                value = testedValueText
            }

            // when
            val model = TextFieldActionMapper.controllerToModel(controller, SimpleBooleanProperty(false), emptySet())

            // then
            model.shouldBeInstanceOfAndCheck<ScrollWheelUpAction> {
                actionBootType shouldBe ActionBootTextField.SCROLL_WHEEL_UP
                numberOfScrolls shouldBe testedValueText.toInt()
                down shouldBe false
            }
        }
        ActionBootTextField.SCROLL_WHEEL_DOWN.name - {
            // given
            val controller = TextFieldActionController(ActionBootTextField.SCROLL_WHEEL_DOWN).apply {
                value = testedValueText
            }

            // when
            val model = TextFieldActionMapper.controllerToModel(controller, SimpleBooleanProperty(false), emptySet())

            // then
            model.shouldBeInstanceOfAndCheck<ScrollWheelDownAction> {
                actionBootType shouldBe ActionBootTextField.SCROLL_WHEEL_DOWN
                numberOfScrolls shouldBe testedValueText.toInt()
                down shouldBe true
            }
        }
    }

})