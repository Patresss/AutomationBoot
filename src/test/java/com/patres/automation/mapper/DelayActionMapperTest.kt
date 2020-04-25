package com.patres.automation.mapper

import com.patres.automation.action.delay.TimeContainer
import com.patres.automation.action.delay.TimeType
import com.patres.automation.gui.controller.model.TimeActionController
import com.patres.automation.helpers.JfxSpec
import com.patres.automation.helpers.shouldNotBeNullAndCheck
import com.patres.automation.mapper.model.TimeActionSerialized
import com.patres.automation.type.ActionBootTime
import io.kotest.matchers.shouldBe

class DelayActionMapperTest : JfxSpec({

    val testedValue = "123"
    val testedDelayType = TimeType.SECONDS

    "Should map serialized to controller" - {
        ActionBootTime.values().map { verifiedAction: ActionBootTime ->
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
        ActionBootTime.values().map { verifiedAction: ActionBootTime ->
            verifiedAction.name {
                // given
                val serializedModel = TimeActionSerialized(verifiedAction, TimeContainer(testedValue.toLong(), testedDelayType))

                // when
                val model = TimeActionMapper.serializedToModel(serializedModel, null)

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
        ActionBootTime.values().map { verifiedAction: ActionBootTime ->
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
        ActionBootTime.values().map { verifiedAction: ActionBootTime ->
            verifiedAction.name {
                // given
                val controller = TimeActionController(verifiedAction).apply {
                    comboBox.value = testedDelayType
                    value = testedValue
                }

                // when
                val model = TimeActionMapper.controllerToModel(controller)

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