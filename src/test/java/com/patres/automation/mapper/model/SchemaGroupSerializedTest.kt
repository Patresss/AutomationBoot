package com.patres.automation.mapper.model

import com.patres.automation.action.SchemaGroupModel
import com.patres.automation.action.mouse.LeftMouseClickAction
import com.patres.automation.action.mouse.MoveMouseAction
import com.patres.automation.helpers.JfxSerializedSpec
import com.patres.automation.helpers.shouldBeInstanceOfAndCheck
import com.patres.automation.helpers.shouldNotBeNullAndCheck
import com.patres.automation.mapper.AutomationMapper
import com.patres.automation.mapper.RootSchemaGroupMapper
import com.patres.automation.type.ActionBootSchema
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.types.shouldBeInstanceOf
import java.io.File

class SchemaGroupSerializedTest : JfxSerializedSpec({

    val actionDirectory = "/actions/SchemaGroup"

    "Should map file to serialized" - {
        ActionBootSchema.SCHEMA_GROUP.name - {
            // given
            val filePath = "$actionDirectory/${ActionBootSchema.SCHEMA_GROUP.name}.ab"

            // when
            val textFromFile = loadTextFromFile(filePath)
            val rootGroupSerialized: RootSchemaGroupSerialized = AutomationMapper.toObject(textFromFile)
            val serializedToModel = RootSchemaGroupMapper.serializedToModel(rootGroupSerialized, File("/"))

            // then
            serializedToModel.schemaGroupModel.shouldNotBeNullAndCheck {
                actions shouldHaveSize 2
                actions[0].shouldBeInstanceOfAndCheck<SchemaGroupModel> {
                    actions shouldHaveSize 3
                    actions[0].shouldBeInstanceOf<MoveMouseAction>()
                    actions[1].shouldBeInstanceOf<LeftMouseClickAction>()
                    actions[2].shouldBeInstanceOfAndCheck<SchemaGroupModel> {
                        actions shouldHaveSize 1
                        actions[0].shouldBeInstanceOf<MoveMouseAction>()
                    }
                }
                actions[1].shouldBeInstanceOf<MoveMouseAction>()
            }
        }
    }

    "Should map file to serialized to file" - {
        ActionBootSchema.SCHEMA_GROUP.name - {
            val filePath = "$actionDirectory/${ActionBootSchema.SCHEMA_GROUP.name}.ab"
            testMapFileToSerializedToFile(filePath)
        }
    }
})