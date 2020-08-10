package com.patres.automation.mapper

import com.patres.automation.gui.controller.box.SchemaGroupController
import com.patres.automation.helpers.JfxSpec
import com.patres.automation.helpers.shouldNotBeNullAndCheck
import com.patres.automation.mapper.model.MousePointActionSerialized
import com.patres.automation.mapper.model.SchemaGroupSerialized
import com.patres.automation.mapper.model.TextFieldActionSerialized
import com.patres.automation.type.ActionBootMousePoint
import com.patres.automation.type.ActionBootSchema
import com.patres.automation.type.ActionBootTextField
import io.kotest.matchers.collections.shouldBeSameSizeAs
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import javafx.beans.property.SimpleBooleanProperty

class SchemaGroupMapperTest : JfxSpec({

    val testedGroupName = "Group test 123"
    val testedNumberOfIterations = "3"
    val testedActionList = listOf(
            MousePointActionSerialized(ActionBootMousePoint.CLICK_LEFT_MOUSE_BUTTON, "(12;34)"),
            TextFieldActionSerialized(ActionBootTextField.SCROLL_WHEEL_UP, "2"))

    "Should map serialized to controller" - {
        ActionBootSchema.values().map { verifiedAction: ActionBootSchema ->
            verifiedAction.name {
                // given
                val serializedModel = SchemaGroupSerialized(testedActionList, testedGroupName, testedNumberOfIterations)

                // when
                val controller = SchemaGroupMapper.serializedToController(serializedModel)

                // then
                controller.shouldNotBeNullAndCheck {
                    abstractBlocks shouldHaveSize testedActionList.size
                    turnOnToggleButton.isSelected shouldBe true
                    groupNameTextField.text shouldBe testedGroupName
                    getNumberOfIteration() shouldBe testedNumberOfIterations.toInt()
                }
            }
        }
    }

    "Should map serialized to model" - {
        ActionBootSchema.values().map { verifiedAction: ActionBootSchema ->
            verifiedAction.name {
                // given
                val serializedModel = SchemaGroupSerialized(testedActionList, testedGroupName, testedNumberOfIterations)

                // when
                val model = SchemaGroupMapper.serializedToModel(serializedModel, SimpleBooleanProperty(false), emptySet())

                // then
                model.shouldNotBeNullAndCheck {
                    actions shouldBeSameSizeAs testedActionList
                    iteration shouldBe testedNumberOfIterations.toInt()
                }
            }
        }
    }

    "Should map controller to serialized" - {
        ActionBootSchema.values().map { verifiedAction: ActionBootSchema ->
            verifiedAction.name {
                // given
                val controller = SchemaGroupController().apply {
                    groupNameTextField.text = testedGroupName
                    iterationsTextField.text = testedNumberOfIterations
                    testedActionList.map { it.serializedToController() }.forEach { addNewAction(it) }
                }

                // when
                val serialized = SchemaGroupMapper.controllerToSerialized(controller)

                // then
                serialized.shouldNotBeNullAndCheck {
                    actionList shouldBeSameSizeAs testedActionList
                    groupName shouldBe testedGroupName
                    numberOfIterations shouldBe testedNumberOfIterations
                }
            }
        }
    }

    "Should map controller to model" - {
        ActionBootSchema.values().map { verifiedAction: ActionBootSchema ->
            verifiedAction.name {
                // given
                val controller = SchemaGroupController().apply {
                    groupNameTextField.text = testedGroupName
                    iterationsTextField.text = testedNumberOfIterations
                    testedActionList.map { it.serializedToController() }.forEach { addNewAction(it) }
                }

                // when
                val model = SchemaGroupMapper.controllerToModel(controller, SimpleBooleanProperty(false), emptySet())

                // then
                model.shouldNotBeNullAndCheck {
                    actions shouldBeSameSizeAs testedActionList
                    iteration shouldBe testedNumberOfIterations.toInt()
                }
            }
        }
    }

})