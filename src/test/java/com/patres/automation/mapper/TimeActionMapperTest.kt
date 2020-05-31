package com.patres.automation.mapper

import com.patres.automation.action.delay.TimeContainer
import com.patres.automation.action.delay.TimeType
import com.patres.automation.gui.controller.model.TimeActionController
import com.patres.automation.helpers.JfxSpec
import com.patres.automation.helpers.shouldNotBeNullAndCheck
import com.patres.automation.mapper.model.TimeActionSerialized
import com.patres.automation.type.ActionBootTextField
import com.patres.automation.type.ActionBootTime
import io.kotest.matchers.shouldBe
import javafx.beans.property.SimpleBooleanProperty

class TimeActionMapperTest : JfxSpec({

    val testedValue = "123"
    val testedDelayType = TimeType.SECONDS
    val mappableActions = listOf(ActionBootTime.DELAY)

    "Should map serialized to controller" - {
        mappableActions.map { verifiedAction: ActionBootTime ->
            verifiedAction.name {
                // given
                val serializedModel = TimeActionSerialized(verifiedAction, TimeContainer(testedValue.toLong(), testedDelayType))

                // when
                val controller = TimeActionMapper.serializedToController(serializedModel)

                // then
                controller.shouldNotBeNullAndCheck {
                    actionBoot shouldBe verifiedAction
                    value shouldBe testedValue
                    comboBox.value shouldBe testedDelayType
                }
            }
        }
    }

    "Should map serialized to model" - {
        mappableActions.map { verifiedAction: ActionBootTime ->
            verifiedAction.name {
                // given
                val serializedModel = TimeActionSerialized(verifiedAction, TimeContainer(testedValue.toLong(), testedDelayType))

                // when
                val model = TimeActionMapper.serializedToModel(serializedModel, SimpleBooleanProperty(false))

                // then
                model.shouldNotBeNullAndCheck {
                    actionBootType shouldBe verifiedAction
                    timeContainer.value shouldBe testedValue.toLong()
                    timeContainer.type shouldBe testedDelayType
                }
            }
        }
    }

    "Should map controller to serialized" - {
        mappableActions.map { verifiedAction: ActionBootTime ->
            verifiedAction.name {
                // given
                val controller = TimeActionController(verifiedAction).apply {
                    comboBox.value = testedDelayType
                    value = testedValue
                }

                // when
                val serialized = TimeActionMapper.controllerToSerialized(controller)

                // then
                serialized.shouldNotBeNullAndCheck {
                    actionBootType shouldBe verifiedAction
                    timeContainer.value shouldBe testedValue.toLong()
                    timeContainer.type shouldBe testedDelayType
                }
            }
        }
    }

    "Should map controller to model" - {
        mappableActions.map { verifiedAction: ActionBootTime ->
            verifiedAction.name {
                // given
                val controller = TimeActionController(verifiedAction).apply {
                    comboBox.value = testedDelayType
                    value = testedValue
                }

                // when
                val model = TimeActionMapper.controllerToModel(controller, SimpleBooleanProperty(false))

                // then
                model.shouldNotBeNullAndCheck {
                    actionBootType shouldBe verifiedAction
                    timeContainer.value shouldBe testedValue.toLong()
                    timeContainer.type shouldBe testedDelayType
                }
            }
        }
    }

})