package com.patres.automation.mapper

import com.patres.automation.action.script.*
import com.patres.automation.action.text.PasteTextFromFileAction
import com.patres.automation.action.text.TypeTextFromFileAction
import com.patres.automation.gui.controller.model.BrowseFileActionController
import com.patres.automation.helpers.JfxSpec
import com.patres.automation.helpers.shouldBeInstanceOfAndCheck
import com.patres.automation.helpers.shouldNotBeNullAndCheck
import com.patres.automation.mapper.model.BrowserActionSerialized
import com.patres.automation.type.ActionBootBrowser
import io.kotest.matchers.shouldBe
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

internal class BrowserActionMapperTest : JfxSpec({

    val testedFilePath = URLDecoder.decode(BrowserActionMapperTest::class.java.getResource("/tested-value/test-file.txt").file, StandardCharsets.UTF_8.toString())
    val testedFileAbPath = URLDecoder.decode(BrowserActionMapperTest::class.java.getResource("/tested-value/test-file.ab").file, StandardCharsets.UTF_8.toString())
    val testedFileResult = "Text 123"

    "Should map serialized to controller" - {
        ActionBootBrowser.values().map { verifiedAction: ActionBootBrowser ->
            verifiedAction.name {
                // given
                val serializedModel = BrowserActionSerialized(verifiedAction, testedFilePath)

                // when
                val controller = BrowserActionMapper.serializedToController(serializedModel)

                // then
                controller.shouldNotBeNullAndCheck {
                    actionBoot shouldBe verifiedAction
                    value shouldBe testedFilePath
                }
            }
        }
    }

    "Should map to model" - {
        ActionBootBrowser.PASTE_TEXT_FROM_FILE.name - {
            // given
            val serializedModel = BrowserActionSerialized(ActionBootBrowser.PASTE_TEXT_FROM_FILE, testedFilePath)

            // when
            val model = BrowserActionMapper.serializedToModel(serializedModel, null)

            // then
            model.shouldBeInstanceOfAndCheck<PasteTextFromFileAction> {
                actionBootType shouldBe ActionBootBrowser.PASTE_TEXT_FROM_FILE
                value shouldBe testedFilePath
            }
        }
        ActionBootBrowser.TYPE_TEXT_FROM_FILE.name - {
            // given
            val serializedModel = BrowserActionSerialized(ActionBootBrowser.TYPE_TEXT_FROM_FILE, testedFilePath)

            // when
            val model = BrowserActionMapper.serializedToModel(serializedModel, null)

            // then
            model.shouldBeInstanceOfAndCheck<TypeTextFromFileAction> {
                actionBootType shouldBe ActionBootBrowser.TYPE_TEXT_FROM_FILE
                getText() shouldBe testedFileResult
            }
        }
        ActionBootBrowser.OPEN_FILE.name - {
            // given
            val serializedModel = BrowserActionSerialized(ActionBootBrowser.OPEN_FILE, testedFilePath)

            // when
            val model = BrowserActionMapper.serializedToModel(serializedModel, null)

            // then
            model.shouldBeInstanceOfAndCheck<OpenFileAction> {
                actionBootType shouldBe ActionBootBrowser.OPEN_FILE
                path shouldBe testedFilePath
            }
        }
        ActionBootBrowser.OPEN_DIRECTORY.name - {
            // given
            val serializedModel = BrowserActionSerialized(ActionBootBrowser.OPEN_DIRECTORY, testedFilePath)

            // when
            val model = BrowserActionMapper.serializedToModel(serializedModel, null)

            // then
            model.shouldBeInstanceOfAndCheck<OpenDirectoryAction> {
                actionBootType shouldBe ActionBootBrowser.OPEN_DIRECTORY
                path shouldBe testedFilePath
            }
        }
        ActionBootBrowser.WINDOWS_SCRIPT_RUN.name - {
            // given
            val serializedModel = BrowserActionSerialized(ActionBootBrowser.WINDOWS_SCRIPT_RUN, testedFilePath)

            // when
            val model = BrowserActionMapper.serializedToModel(serializedModel, null)

            // then
            model.shouldBeInstanceOfAndCheck<WindowsRunScriptAction> {
                actionBootType shouldBe ActionBootBrowser.WINDOWS_SCRIPT_RUN
                path shouldBe testedFilePath
            }
        }
        ActionBootBrowser.WINDOWS_SCRIPT_RUN_AND_WAITE.name - {
            // given
            val serializedModel = BrowserActionSerialized(ActionBootBrowser.WINDOWS_SCRIPT_RUN_AND_WAITE, testedFilePath)

            // when
            val model = BrowserActionMapper.serializedToModel(serializedModel, null)

            // then
            model.shouldBeInstanceOfAndCheck<WindowsRunAndWaitScriptAction> {
                actionBootType shouldBe ActionBootBrowser.WINDOWS_SCRIPT_RUN_AND_WAITE
                path shouldBe testedFilePath
            }
        }
        ActionBootBrowser.RUN_EXISTING_SCHEMA.name - {
            // given
            val serializedModel = BrowserActionSerialized(ActionBootBrowser.RUN_EXISTING_SCHEMA, testedFileAbPath)

            // when
            val model = BrowserActionMapper.serializedToModel(serializedModel, null)

            // then
            model.shouldBeInstanceOfAndCheck<RunExistingSchemaAction> {
                actionBootType shouldBe ActionBootBrowser.RUN_EXISTING_SCHEMA
            }
        }
    }
    "Should map controller to serialized" - {
        ActionBootBrowser.values().map { verifiedAction: ActionBootBrowser ->
            verifiedAction.name {
                // given
                val serializedModel = BrowseFileActionController(verifiedAction).apply {
                    value = testedFilePath
                }

                // when
                val model = BrowserActionMapper.controllerToSerialized(serializedModel)

                // then
                model.shouldNotBeNullAndCheck {
                    actionBootType shouldBe verifiedAction
                    path shouldBe testedFilePath
                }
            }
        }
    }
    "Should map controller to model" - {
        ActionBootBrowser.PASTE_TEXT_FROM_FILE.name - {
            // given
            val serializedModel = BrowseFileActionController(ActionBootBrowser.PASTE_TEXT_FROM_FILE).apply {
                value = testedFilePath
            }

            // when
            val model = BrowserActionMapper.controllerToModel(serializedModel)

            // then
            model.shouldBeInstanceOfAndCheck<PasteTextFromFileAction> {
                actionBootType shouldBe ActionBootBrowser.PASTE_TEXT_FROM_FILE
                value shouldBe testedFilePath
            }
        }
        ActionBootBrowser.TYPE_TEXT_FROM_FILE.name - {
            // given
            val serializedModel = BrowseFileActionController(ActionBootBrowser.TYPE_TEXT_FROM_FILE).apply {
                value = testedFilePath
            }

            // when
            val model = BrowserActionMapper.controllerToModel(serializedModel)

            // then
            model.shouldBeInstanceOfAndCheck<TypeTextFromFileAction> {
                actionBootType shouldBe ActionBootBrowser.TYPE_TEXT_FROM_FILE
                getText() shouldBe testedFileResult
            }
        }
        ActionBootBrowser.OPEN_FILE.name - {
            // given
            val serializedModel = BrowseFileActionController(ActionBootBrowser.OPEN_FILE).apply {
                value = testedFilePath
            }

            // when
            val model = BrowserActionMapper.controllerToModel(serializedModel)

            // then
            model.shouldBeInstanceOfAndCheck<OpenFileAction> {
                actionBootType shouldBe ActionBootBrowser.OPEN_FILE
                path shouldBe testedFilePath
            }
        }
        ActionBootBrowser.OPEN_DIRECTORY.name - {
            // given
            val serializedModel = BrowseFileActionController(ActionBootBrowser.OPEN_DIRECTORY).apply {
                value = testedFilePath
            }

            // when
            val model = BrowserActionMapper.controllerToModel(serializedModel)

            // then
            model.shouldBeInstanceOfAndCheck<OpenDirectoryAction> {
                actionBootType shouldBe ActionBootBrowser.OPEN_DIRECTORY
                path shouldBe testedFilePath
            }
        }
        ActionBootBrowser.WINDOWS_SCRIPT_RUN.name - {
            // given
            val serializedModel = BrowseFileActionController(ActionBootBrowser.WINDOWS_SCRIPT_RUN).apply {
                value = testedFilePath
            }

            // when
            val model = BrowserActionMapper.controllerToModel(serializedModel)

            // then
            model.shouldBeInstanceOfAndCheck<WindowsRunScriptAction> {
                actionBootType shouldBe ActionBootBrowser.WINDOWS_SCRIPT_RUN
                path shouldBe testedFilePath
            }
        }
        ActionBootBrowser.WINDOWS_SCRIPT_RUN_AND_WAITE.name - {
            // given
            val serializedModel = BrowseFileActionController(ActionBootBrowser.WINDOWS_SCRIPT_RUN_AND_WAITE).apply {
                value = testedFilePath
            }

            // when
            val model = BrowserActionMapper.controllerToModel(serializedModel)

            // then
            model.shouldBeInstanceOfAndCheck<WindowsRunAndWaitScriptAction> {
                actionBootType shouldBe ActionBootBrowser.WINDOWS_SCRIPT_RUN_AND_WAITE
                path shouldBe testedFilePath
            }
        }
        ActionBootBrowser.RUN_EXISTING_SCHEMA.name - {
            // given
            val serializedModel = BrowseFileActionController(ActionBootBrowser.RUN_EXISTING_SCHEMA).apply {
                value = testedFileAbPath
            }

            // when
            val model = BrowserActionMapper.controllerToModel(serializedModel)

            // then
            model.shouldBeInstanceOfAndCheck<RunExistingSchemaAction> {
                actionBootType shouldBe ActionBootBrowser.RUN_EXISTING_SCHEMA
            }
        }
    }

})