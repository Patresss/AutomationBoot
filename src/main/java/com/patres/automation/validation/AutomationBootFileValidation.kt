package com.patres.automation.validation

import com.patres.automation.excpetion.AutomationBootFileIsInvalidException
import com.patres.automation.file.FileType
import com.patres.automation.mapper.RootSchemaGroupMapper
import com.patres.automation.settings.LanguageManager

class AutomationBootFileValidation : Validationable() {

    private val extensionType = FileType.AUTOMATION_BOOT

    override fun isValid(value: String): Boolean {
        return FileExistValidation().isValid(value) && FileExtensionValidation(extensionType).isValid(value) && isAutomationBootFileValid(value)
    }

    private fun isAutomationBootFileValid(value: String): Boolean {
        return try {
            val schemaModel = RootSchemaGroupMapper.serializedToMainSchemaModel(value, null)
            schemaModel.validate()
            true
        } catch (e: Throwable) {
            false
        }

    }

    override fun throwException(value: String) {
        throw AutomationBootFileIsInvalidException(value)
    }

    override fun getErrorMessageStringBinding() = LanguageManager.createStringBinding("error.invalidAutomationBootFile")
}
