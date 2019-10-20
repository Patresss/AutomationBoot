package com.patres.automation.type

import com.patres.automation.file.FileType
import com.patres.automation.gui.controller.model.AutomationController
import com.patres.automation.gui.controller.model.BrowseFileActionController
import com.patres.automation.validation.FileExistValidation
import com.patres.automation.validation.FileExtensionValidation
import com.patres.automation.validation.FileOrDirectoryExistValidation
import com.patres.automation.validation.Validationable


enum class ActionBootBrowser(
        val bundleName: String,
        val validation: Validationable? = null,
        val fileType: FileType? = null
) : ActionBootable {

    PASTE_TEXT_FROM_FILE("robot.action.keyboard.paste.file", FileExistValidation()),
    TYPE_TEXT_FROM_FILE("robot.action.keyboard.type.file", FileExistValidation()),

    OPEN_FILE_OR_DIRECTORY("robot.action.open.fileOrDirectory", FileOrDirectoryExistValidation()),

    WINDOWS_SCRIPT_RUN("robot.action.script.windows.run", FileExtensionValidation(FileType.BAT)),
    WINDOWS_SCRIPT_RUN_AND_WAITE("robot.action.script.windows.runAndWait", FileExtensionValidation(FileType.BAT));

    override fun validation(): Validationable? {
        return this.validation
    }

    override fun bundleName(): String {
        return this.bundleName
    }

    override fun createController(): () -> AutomationController<*> = { BrowseFileActionController(this) }

}
