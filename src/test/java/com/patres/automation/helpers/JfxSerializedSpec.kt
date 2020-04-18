package com.patres.automation.helpers

import com.patres.automation.mapper.AutomationMapper
import com.patres.automation.mapper.RootSchemaGroupMapper
import com.patres.automation.mapper.model.RootSchemaGroupSerialized
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import java.io.File

open class JfxSerializedSpec(body: FreeSpec.() -> Unit = {}) : JfxSpec(body) {

    companion object {
        fun testMapFileToSerializedToFile(filePath: String) {
            // given

            // when
            val textFromFile = JfxSerializedSpec::class.java.getResource(filePath).readText()
            val rootGroupSerialized: RootSchemaGroupSerialized = AutomationMapper.toObject(textFromFile)
            val serializedToModel = RootSchemaGroupMapper.serializedToModel(rootGroupSerialized, File("/"))

            val rootSchemaGroupSerialized = RootSchemaGroupMapper.modelToSerialize(serializedToModel)
            val json = AutomationMapper.toJson(rootSchemaGroupSerialized)

            // then
            textFromFile shouldBe json
        }

        fun String.replacePath() = this.replace("\${PROJECT_PATH}", System.getProperty("user.dir").replace("\\", "\\\\"))

        fun loadTextFromFile(filePath: String) = JfxSerializedSpec::class.java.getResource(filePath).readText().replacePath()
    }


}