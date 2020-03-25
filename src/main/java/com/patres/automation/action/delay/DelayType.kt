package com.patres.automation.action.delay

import java.util.concurrent.TimeUnit

enum class DelayType(private val timeUnit: TimeUnit) {

    HOURS(TimeUnit.HOURS),
    MINUTES(TimeUnit.MINUTES),
    SECONDS(TimeUnit.SECONDS),
    MILLISECONDS(TimeUnit.MILLISECONDS);

    fun toMilliseconds(value: Long) = timeUnit.toMillis(value)

    fun bundleName() = "robot.action.delay.$this.name"
    fun bundleShortName() = "robot.action.delay.$this.shortName"

}