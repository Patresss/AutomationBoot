package com.patres.automation.type

import com.patres.automation.ActionBootControllerType
import com.patres.automation.file.FileType
import com.patres.automation.gui.controller.model.BrowseFileActionController
import com.patres.automation.model.RootSchemaGroupModel
import com.patres.automation.validation.FileExistValidation
import com.patres.automation.validation.FileExtensionValidation
import com.patres.automation.validation.FileOrDirectoryExistValidation
import com.patres.automation.validation.Validationable


enum class ActionBootBrowser(
        val bundleName: String,
        val validation: Validationable? = null,
        val controllerType: ActionBootControllerType,
        val fileType: FileType? = null
) : ActionBootable {

    PASTE_TEXT_FROM_FILE("robot.action.keyboard.paste.file", FileExistValidation(), ActionBootControllerType.BROWSE_FILE),
    TYPE_TEXT_FROM_FILE("robot.action.keyboard.type.file", FileExistValidation(), ActionBootControllerType.BROWSE_FILE),

    OPEN_FILE_OR_DIRECTORY("robot.action.open.fileOrDirectory", FileOrDirectoryExistValidation(), ActionBootControllerType.BROWSE_FILE),

    WINDOWS_SCRIPT_RUN("robot.action.script.windows.run", FileExtensionValidation(FileType.BAT), ActionBootControllerType.BROWSE_FILE_BAT),
    WINDOWS_SCRIPT_RUN_AND_WAITE("robot.action.script.windows.runAndWait", FileExtensionValidation(FileType.BAT), ActionBootControllerType.BROWSE_FILE_BAT);

    override fun validation(): Validationable? {
        return this.validation
    }

    override fun bundleName(): String {
        return this.bundleName
    }

    override fun controllerType(): ActionBootControllerType {
        return this.controllerType
    }

    override fun createController() = { root: RootSchemaGroupModel -> BrowseFileActionController(root, root.controller.getSelectedSchemaGroupModel(), this) }

}
