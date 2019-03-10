package com.patres.automation.action.script

import com.patres.automation.action.TextActionModel
import com.patres.automation.gui.controller.model.BrowseFileActionController
import com.patres.automation.model.RootSchemaGroupModel
import com.patres.automation.model.SchemaGroupModel
import com.patres.automation.validation.AbstractValidation
import com.patres.automation.validation.FileExtensionValidation
import java.io.File


abstract class WindowsScriptAction(
        root: RootSchemaGroupModel,
        parent: SchemaGroupModel = root.getSelectedSchemaGroupModel()
) : TextActionModel<BrowseFileActionController>(root, parent) {

    companion object {
        const val extension = ".bat"
        const val extensionType = "Script"
    }

    final override val controller: BrowseFileActionController = BrowseFileActionController(this, extension = extension, extensionType = extensionType)

    override var validator: AbstractValidation? = FileExtensionValidation(controller, extension).also { it.activateControlListener() }

    override fun runAction() {
        val file = File(getActionValue())

        val processBuilder = ProcessBuilder().apply {
            command(file.absolutePath)
//            directory(file.parentFile)
        }

        if (shouldWait()) {
            val process = processBuilder.start()
            process.waitFor()
        }
    }

    abstract fun shouldWait(): Boolean

}