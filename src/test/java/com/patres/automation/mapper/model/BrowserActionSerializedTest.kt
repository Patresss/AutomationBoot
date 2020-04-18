package com.patres.automation.mapper.model

import com.patres.automation.action.script.*
import com.patres.automation.action.text.PasteTextFromFileAction
import com.patres.automation.action.text.TypeTextFromFileAction
import com.patres.automation.helpers.JfxSerializedSpec
import com.patres.automation.helpers.shouldNotBeNullAndCheck
import com.patres.automation.mapper.AutomationMapper
import com.patres.automation.mapper.RootSchemaGroupMapper
import com.patres.automation.type.ActionBootBrowser
import io.kotest.matchers.types.shouldBeInstanceOf
import java.io.File

internal class BrowserActionSerializedTest : JfxSerializedSpec({

    val actionDirectory = "/actions/BrowserAction"

    "Should map file to serialized" - {
        ActionBootBrowser.PASTE_TEXT_FROM_FILE.name - {
            // given
            val filePath = "$actionDirectory/${ActionBootBrowser.PASTE_TEXT_FROM_FILE.name}.ab"

            // when
            val textFromFile = loadTextFromFile(filePath)
            val rootGroupSerialized: RootSchemaGroupSerialized = AutomationMapper.toObject(textFromFile)
            val serializedToModel = RootSchemaGroupMapper.serializedToModel(rootGroupSerialized, File("/"))

            // then
            serializedToModel.schemaGroupModel.shouldNotBeNullAndCheck {
                actions[0].shouldBeInstanceOf<PasteTextFromFileAction>()
            }
        }
        ActionBootBrowser.TYPE_TEXT_FROM_FILE.name - {
            // given
            val filePath = "$actionDirectory/${ActionBootBrowser.TYPE_TEXT_FROM_FILE.name}.ab"

            // when
            val textFromFile = loadTextFromFile(filePath)
            val rootGroupSerialized: RootSchemaGroupSerialized = AutomationMapper.toObject(textFromFile)
            val serializedToModel = RootSchemaGroupMapper.serializedToModel(rootGroupSerialized, File("/"))

            // then
            serializedToModel.schemaGroupModel.shouldNotBeNullAndCheck {
                actions[0].shouldBeInstanceOf<TypeTextFromFileAction>()
            }
        }
        ActionBootBrowser.OPEN_FILE.name - {
            // given
            val filePath = "$actionDirectory/${ActionBootBrowser.OPEN_FILE.name}.ab"

            // when
            val textFromFile = loadTextFromFile(filePath)
            val rootGroupSerialized: RootSchemaGroupSerialized = AutomationMapper.toObject(textFromFile)
            val serializedToModel = RootSchemaGroupMapper.serializedToModel(rootGroupSerialized, File("/"))

            // then
            serializedToModel.schemaGroupModel.shouldNotBeNullAndCheck {
                actions[0].shouldBeInstanceOf<OpenFileAction>()
            }
        }
        ActionBootBrowser.OPEN_DIRECTORY.name - {
            // given
            val filePath = "$actionDirectory/${ActionBootBrowser.OPEN_DIRECTORY.name}.ab"

            // when
            val textFromFile = loadTextFromFile(filePath)
            val rootGroupSerialized: RootSchemaGroupSerialized = AutomationMapper.toObject(textFromFile)
            val serializedToModel = RootSchemaGroupMapper.serializedToModel(rootGroupSerialized, File("/"))

            // then
            serializedToModel.schemaGroupModel.shouldNotBeNullAndCheck {
                actions[0].shouldBeInstanceOf<OpenDirectoryAction>()
            }
        }
        ActionBootBrowser.WINDOWS_SCRIPT_RUN.name - {
            // given
            val filePath = "$actionDirectory/${ActionBootBrowser.WINDOWS_SCRIPT_RUN.name}.ab"

            // when
            val textFromFile = loadTextFromFile(filePath)
            val rootGroupSerialized: RootSchemaGroupSerialized = AutomationMapper.toObject(textFromFile)
            val serializedToModel = RootSchemaGroupMapper.serializedToModel(rootGroupSerialized, File("/"))

            // then
            serializedToModel.schemaGroupModel.shouldNotBeNullAndCheck {
                actions[0].shouldBeInstanceOf<WindowsScriptAction>()
            }
        }
        ActionBootBrowser.WINDOWS_SCRIPT_RUN_AND_WAITE.name - {
            // given
            val filePath = "$actionDirectory/${ActionBootBrowser.WINDOWS_SCRIPT_RUN_AND_WAITE.name}.ab"

            // when
            val textFromFile = loadTextFromFile(filePath)
            val rootGroupSerialized: RootSchemaGroupSerialized = AutomationMapper.toObject(textFromFile)
            val serializedToModel = RootSchemaGroupMapper.serializedToModel(rootGroupSerialized, File("/"))

            // then
            serializedToModel.schemaGroupModel.shouldNotBeNullAndCheck {
                actions[0].shouldBeInstanceOf<WindowsRunAndWaitScriptAction>()
            }
        }
        ActionBootBrowser.RUN_EXISTING_SCHEMA.name - {
            // given
            val filePath = "$actionDirectory/${ActionBootBrowser.RUN_EXISTING_SCHEMA.name}.ab"

            // when
            val textFromFile = loadTextFromFile(filePath)
            val rootGroupSerialized: RootSchemaGroupSerialized = AutomationMapper.toObject(textFromFile)
            val serializedToModel = RootSchemaGroupMapper.serializedToModel(rootGroupSerialized, File("/"))

            // then
            serializedToModel.schemaGroupModel.shouldNotBeNullAndCheck {
                actions[0].shouldBeInstanceOf<RunExistingSchemaAction>()
            }
        }
    }

    "Should map file to serialized to file" - {
        ActionBootBrowser.values().forEach { verifiedAction ->
            verifiedAction.name - {
                val filePath = "$actionDirectory/${verifiedAction.name}.ab"
                testMapFileToSerializedToFile(filePath)
            }
        }
    }
})