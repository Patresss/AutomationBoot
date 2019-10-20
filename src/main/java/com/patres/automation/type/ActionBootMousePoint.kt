package com.patres.automation.type

import com.patres.automation.gui.controller.model.AutomationController
import com.patres.automation.gui.controller.model.MousePointActionController
import com.patres.automation.validation.PointVectorValidation
import com.patres.automation.validation.Validationable


enum class ActionBootMousePoint(
        val bundleName: String,
        val validation: Validationable? = PointVectorValidation()
) : ActionBootable {

    MOVE_MOUSE("robot.action.moveMouse"),

    CLICK_LEFT_MOUSE_BUTTON("robot.action.mouseClick.left"),
    CLICK_MIDDLE_MOUSE_BUTTON("robot.action.mouseClick.middle"),
    CLICK_RIGHT_MOUSE_BUTTON("robot.action.mouseClick.right"),

    DOUBLE_CLICK_LEFT_MOUSE_BUTTON("robot.action.doubleMouseClick.left"),
    DOUBLE_CLICK_MIDDLE_MOUSE_BUTTON("robot.action.doubleMouseClick.middle"),
    DOUBLE_CLICK_RIGHT_MOUSE_BUTTON("robot.action.doubleMouseClick.right"),

    PRESS_LEFT_MOUSE_BUTTON("robot.action.pressMouseClick.left"),
    PRESS_MIDDLE_MOUSE_BUTTON("robot.action.pressMouseClick.middle"),
    PRESS_RIGHT_MOUSE_BUTTON("robot.action.pressMouseClick.right"),

    RELEASE_LEFT_MOUSE_BUTTON("robot.action.releaseMouseClick.left"),
    RELEASE_MIDDLE_MOUSE_BUTTON("robot.action.releaseMouseClick.middle"),
    RELEASE_RIGHT_MOUSE_BUTTON("robot.action.releaseMouseClick.right");


    override fun validation(): Validationable? {
        return this.validation
    }

    override fun bundleName(): String {
        return this.bundleName
    }

    override fun createController(): () -> AutomationController<*> = { MousePointActionController(this) }

}
