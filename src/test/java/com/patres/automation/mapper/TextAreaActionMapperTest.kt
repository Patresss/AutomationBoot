package com.patres.automation.mapper

import com.patres.automation.action.text.PasteTextFromFieldAction
import com.patres.automation.action.text.TypeTextFromFieldAction
import com.patres.automation.gui.controller.model.TextAreaActionController
import com.patres.automation.helpers.JfxSpec
import com.patres.automation.helpers.shouldBeInstanceOfAndCheck
import com.patres.automation.helpers.shouldNotBeNullAndCheck
import com.patres.automation.mapper.model.TextAreaActionSerialized
import com.patres.automation.type.ActionBootTextArea
import io.kotest.matchers.shouldBe
import javafx.beans.property.SimpleBooleanProperty

class TextAreaActionMapperTest : JfxSpec({

    val testedValueText = "Test - 123"

    "Should map serialized to controller" - {
        ActionBootTextArea.values().map { verifiedAction: ActionBootTextArea ->
            verifiedAction.name {
                // given
                val serializedModel = TextAreaActionSerialized(verifiedAction, testedValueText)

                // when
                val controller = TextAreaActionMapper.serializedToController(serializedModel)

                // then
                controller.shouldNotBeNullAndCheck {
                    value shouldBe testedValueText
                }
            }
        }
    }

    "Should map serialized to model" - {
        ActionBootTextArea.PASTE_TEXT.name {
            // given
            val serializedModel = TextAreaActionSerialized(ActionBootTextArea.PASTE_TEXT, testedValueText)

            // when
            val model = TextAreaActionMapper.serializedToModel(serializedModel, SimpleBooleanProperty(false))

            // then
            model.shouldBeInstanceOfAndCheck<PasteTextFromFieldAction> {
                value shouldBe testedValueText
            }
        }
        ActionBootTextArea.TYPE_TEXT.name {
            // given
            val serializedModel = TextAreaActionSerialized(ActionBootTextArea.TYPE_TEXT, testedValueText)

            // when
            val model = TextAreaActionMapper.serializedToModel(serializedModel, SimpleBooleanProperty(false))

            // then
            model.shouldBeInstanceOfAndCheck<TypeTextFromFieldAction> {
                getText() shouldBe testedValueText
            }
        }
    }
    "Should map controller to serialized" - {
        ActionBootTextArea.values().map { verifiedAction: ActionBootTextArea ->
            verifiedAction.name {
                // given
                val controller = TextAreaActionController(verifiedAction).apply {
                    value = testedValueText
                }

                // when
                val serialized = TextAreaActionMapper.controllerToSerialized(controller)

                // then
                serialized.shouldNotBeNullAndCheck {
                    actionBootType shouldBe verifiedAction
                    value shouldBe testedValueText
                }
            }
        }
    }
    "Should map controller to model" - {
        ActionBootTextArea.PASTE_TEXT.name - {
            // given
            val controller = TextAreaActionController(ActionBootTextArea.PASTE_TEXT).apply {
                value = testedValueText
            }

            // when
            val model = TextAreaActionMapper.controllerToModel(controller, SimpleBooleanProperty(false))

            // then
            model.shouldBeInstanceOfAndCheck<PasteTextFromFieldAction> {
                actionBootType shouldBe ActionBootTextArea.PASTE_TEXT
                value shouldBe testedValueText
            }
        }
        ActionBootTextArea.TYPE_TEXT.name - {
            // given
            val controller = TextAreaActionController(ActionBootTextArea.TYPE_TEXT).apply {
                value = testedValueText
            }

            // when
            val model = TextAreaActionMapper.controllerToModel(controller, SimpleBooleanProperty(false))

            // then
            model.shouldBeInstanceOfAndCheck<TypeTextFromFieldAction> {
                actionBootType shouldBe ActionBootTextArea.TYPE_TEXT
                getText() shouldBe testedValueText
            }
        }
    }

})