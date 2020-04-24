package com.patres.automation.type

import com.patres.automation.file.FileType
import com.patres.automation.gui.controller.box.AbstractBox
import com.patres.automation.gui.controller.box.ActionBox
import com.patres.automation.gui.controller.model.BrowseFileActionController
import com.patres.automation.validation.*


enum class ActionBootBrowser(
        val bundleName: String,
        val validation: Validationable? = null,
        val fileType: FileType? = null,
        val director: Boolean = false
) : ActionBootable {

    PASTE_TEXT_FROM_FILE("robot.action.keyboard.paste.file", FileExistValidation()),
    TYPE_TEXT_FROM_FILE("robot.action.keyboard.type.file", FileExistValidation()),

    OPEN_FILE("robot.action.open.file", FileExistValidation()),
    OPEN_DIRECTORY("robot.action.open.directory", DirectoryExistValidation(), director = true),

    WINDOWS_SCRIPT_RUN("robot.action.script.windows.run", FileExtensionValidation(FileType.BAT)),
    WINDOWS_SCRIPT_RUN_AND_WAITE("robot.action.script.windows.runAndWait", FileExtensionValidation(FileType.BAT)),

    RUN_EXISTING_SCHEMA("robot.action.runExistingSchema", AutomationBootFileValidation());

    override fun validation(): Validationable? {
        return this.validation
    }

    override fun bundleName(): String {
        return this.bundleName
    }

    override fun createActinBox(): () -> AbstractBox<*> = { ActionBox(BrowseFileActionController(this)) }

}
