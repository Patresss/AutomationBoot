package com.patres.automation.mapper

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

object AutomationMapper {

    val mapper = jacksonObjectMapper()

    inline fun <reified T> toObject(json: String): T = mapper.readValue(json)

    fun toJson(model: Any): String = mapper.writeValueAsString(model)

}