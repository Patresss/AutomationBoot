package com.patres.automation.action.delay

data class TimeContainer(
        val value: Long,
        val type: TimeType = TimeType.MILLISECONDS
) {

    fun calculateMilliseconds() = type.toMilliseconds(value)

}