package com.patres.automation.helpers

import com.patres.automation.mapper.AutomationMapper
import com.patres.automation.mapper.RootSchemaGroupMapper
import com.patres.automation.mapper.model.RootSchemaGroupSerialized
import com.patres.automation.system.ApplicationInfo
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import java.io.File

open class JfxSerializedSpec(body: FreeSpec.() -> Unit = {}) : JfxSpec(body) {

    companion object {
        fun testMapFileToSerializedToFile(filePath: String) {
            // given
            val textFromFile = loadTextFromFile(filePath)
            val rootGroupSerialized: RootSchemaGroupSerialized = AutomationMapper.toObject(textFromFile)

            // when
            val controller = RootSchemaGroupMapper.serializedToController(rootGroupSerialized, File("/"))

            val rootSchemaGroupSerialized = RootSchemaGroupMapper.controllerToSerialize(controller)
            val json = AutomationMapper.toJson(rootSchemaGroupSerialized)

            // then
            json shouldBe textFromFile
        }

        fun String.replaceProperties() = this
                .replace("\${PROJECT_PATH}", System.getProperty("user.dir").replace("\\", "\\\\"))
                .replace("\${APPLICATION_VERSION}", ApplicationInfo.version)

        fun loadTextFromFile(filePath: String) = JfxSerializedSpec::class.java.getResource(filePath).readText().replaceProperties()
    }


}