package com.patres.automation.validation

import com.patres.automation.excpetion.ApplicationException
import com.patres.automation.excpetion.AutomationBootFileIsInvalidException
import com.patres.automation.file.FileType
import com.patres.automation.mapper.RootSchemaGroupMapper
import com.patres.automation.parameter.sent.SentParameterConverter
import com.patres.automation.settings.LanguageManager
import javafx.beans.binding.Bindings
import javafx.beans.binding.StringBinding
import javafx.beans.property.SimpleBooleanProperty
import java.util.concurrent.Callable

class AutomationBootFileValidation : Validationable() {

    private val extensionType = FileType.AUTOMATION_BOOT

    override fun isValidBySpecificValidator(value: String): Boolean {
        val path = if (value.contains("?")) value.split("?")[0] else value
        return FileExistValidation().isValid(path) && FileExtensionValidation(extensionType).isValid(path) && isAutomationBootFileValid(value)
    }

    override fun check(value: String) {
        if (!isValid(value)) {
            try {
                validateAutomationBootFileValid(value)
                throwException(value)
            } catch (e: ApplicationException) {
                throwException("$value (${e.message})")
            }
        }
    }

    private fun isAutomationBootFileValid(value: String): Boolean {
        return try {
            validateAutomationBootFileValid(value)
            true
        } catch (e: Throwable) {
            false
        }
    }

    private fun validateAutomationBootFileValid(value: String) {
        val parameters = SentParameterConverter(value).calculateParameters()
        val path = if (value.contains("?")) value.split("?")[0] else value
        val schemaModel = RootSchemaGroupMapper.serializedToMainSchemaModel(path, SimpleBooleanProperty(false), parameters)
        schemaModel.validate()
    }

    override fun throwException(value: String) {
        throw AutomationBootFileIsInvalidException(value)
    }

    override fun getErrorMessageStringBinding(textValue: String?): StringBinding {
        if (textValue != null) {
            try {
                validateAutomationBootFileValid(textValue)
            } catch (e: ApplicationException) {
                return Bindings.createStringBinding(Callable { e.message })
            } catch (e: Exception) {
                return LanguageManager.createStringBinding("error.invalidAutomationBootFile")
            }
        }
        return LanguageManager.createStringBinding("error.invalidAutomationBootFile")
    }
}
