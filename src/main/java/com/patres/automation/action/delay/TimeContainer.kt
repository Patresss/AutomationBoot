package com.patres.automation.action.delay

import com.patres.automation.util.extension.toLongOrZero

data class TimeContainer(
        val value: String,
        val type: TimeType = TimeType.MILLISECONDS
) {

    fun calculateMilliseconds() = type.toMilliseconds(value.toLongOrZero())

}