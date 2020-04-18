package com.patres.automation.mapper

import com.patres.automation.action.delay.DelayType
import com.patres.automation.gui.controller.model.DelayActionController
import com.patres.automation.helpers.JfxSpec
import com.patres.automation.helpers.shouldNotBeNullAndCheck
import com.patres.automation.mapper.model.DelayActionSerialized
import com.patres.automation.type.ActionBootDelay
import io.kotest.matchers.shouldBe

class DelayActionMapperTest : JfxSpec({

    val testedValue = "123"
    val testedDelayType = DelayType.SECONDS

    "Should map serialized to controller" - {
        ActionBootDelay.values().map { verifiedAction: ActionBootDelay ->
            verifiedAction.name {
                // given
                val serializedModel = DelayActionSerialized(verifiedAction, testedValue, testedDelayType)

                // when
                val controller = DelayActionMapper.serializedToController(serializedModel)

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
        ActionBootDelay.values().map { verifiedAction: ActionBootDelay ->
            verifiedAction.name {
                // given
                val serializedModel = DelayActionSerialized(verifiedAction, testedValue, testedDelayType)

                // when
                val model = DelayActionMapper.serializedToModel(serializedModel, null)

                // then
                model.shouldNotBeNullAndCheck {
                    actionBootType shouldBe verifiedAction
                    delay shouldBe testedValue.toLong()
                    delayType shouldBe testedDelayType
                }
            }
        }
    }

    "Should map controller to serialized" - {
        ActionBootDelay.values().map { verifiedAction: ActionBootDelay ->
            verifiedAction.name {
                // given
                val controller = DelayActionController(verifiedAction).apply {
                    comboBox.value = testedDelayType
                    value = testedValue
                }

                // when
                val serialized = DelayActionMapper.controllerToSerialized(controller)

                // then
                serialized.shouldNotBeNullAndCheck {
                    actionBootType shouldBe verifiedAction
                    value shouldBe testedValue
                    delayType shouldBe testedDelayType
                }
            }
        }
    }

    "Should map controller to model" - {
        ActionBootDelay.values().map { verifiedAction: ActionBootDelay ->
            verifiedAction.name {
                // given
                val controller = DelayActionController(verifiedAction).apply {
                    comboBox.value = testedDelayType
                    value = testedValue
                }

                // when
                val model = DelayActionMapper.controllerToModel(controller)

                // then
                model.shouldNotBeNullAndCheck {
                    actionBootType shouldBe verifiedAction
                    delay shouldBe testedValue.toLong()
                    delayType shouldBe testedDelayType
                }
            }
        }
    }

})