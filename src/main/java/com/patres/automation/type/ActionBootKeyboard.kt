package com.patres.automation.type

import com.patres.automation.gui.controller.box.AbstractBox
import com.patres.automation.gui.controller.box.ActionBox
import com.patres.automation.gui.controller.model.KeyboardButtonActionController
import com.patres.automation.validation.Validationable


enum class ActionBootKeyboard(
        val bundleName: String,
        val validation: Validationable? = null
) : ActionBootable {

    PRESS_KEYBOARD_BUTTON("robot.action.keyboard.press"),
    HOLD_KEYBOARD_BUTTON("robot.action.keyboard.hold"),
    RELEASE_KEYBOARD_BUTTON("robot.action.keyboard.release"),

    RUN_KEYS_SETTINGS("settings.runKeys"),
    STOP_KEYS_SETTINGS("settings.stopKeys"),
    START_RECORDING_KEYS_SETTINGS("settings.record.startKeys"),
    STOP_RECORDING_KEYS_SETTINGS("settings.record.stopKeys");


    override fun validation(): Validationable? {
        return this.validation
    }

    override fun bundleName(): String {
        return this.bundleName
    }

    override fun createNewActionBox(): () -> AbstractBox<*> = { ActionBox(KeyboardButtonActionController(this)) }

}
