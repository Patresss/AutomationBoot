package com.patres.automation.mapper

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

object AutomationMapper {

    val mapper = jacksonObjectMapper().apply {
        enable(SerializationFeature.INDENT_OUTPUT)  // pretty-printing
        disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
    }

    inline fun <reified T> toObject(json: String): T = mapper.readValue(json)

    fun toJson(model: Any): String = mapper.writeValueAsString(model)

}