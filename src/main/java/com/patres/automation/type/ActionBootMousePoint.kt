package com.patres.automation.type

import com.patres.automation.ActionBootControllerType
import com.patres.automation.gui.controller.model.AutomationController
import com.patres.automation.gui.controller.model.MousePointActionController
import com.patres.automation.model.RootSchemaGroupModel
import com.patres.automation.validation.PointVectorValidation
import com.patres.automation.validation.Validationable


enum class ActionBootMousePoint(
        val bundleName: String,
        private val controllerType: ActionBootControllerType,
        val validation: Validationable = PointVectorValidation()
) : ActionBootable {

    MOVE_MOUSE("robot.action.moveMouse", ActionBootControllerType.MOUSE_POINT),

    CLICK_LEFT_MOUSE_BUTTON("robot.action.mouseClick.left", ActionBootControllerType.MOUSE_POINT),
    CLICK_MIDDLE_MOUSE_BUTTON("robot.action.mouseClick.middle", ActionBootControllerType.MOUSE_POINT),
    CLICK_RIGHT_MOUSE_BUTTON("robot.action.mouseClick.right", ActionBootControllerType.MOUSE_POINT),

    DOUBLE_CLICK_LEFT_MOUSE_BUTTON("robot.action.doubleMouseClick.left", ActionBootControllerType.MOUSE_POINT),
    DOUBLE_CLICK_MIDDLE_MOUSE_BUTTON("robot.action.doubleMouseClick.middle", ActionBootControllerType.MOUSE_POINT),
    DOUBLE_CLICK_RIGHT_MOUSE_BUTTON("robot.action.doubleMouseClick.right", ActionBootControllerType.MOUSE_POINT),

    PRESS_LEFT_MOUSE_BUTTON("robot.action.pressMouseClick.left", ActionBootControllerType.MOUSE_POINT),
    PRESS_MIDDLE_MOUSE_BUTTON("robot.action.pressMouseClick.middle", ActionBootControllerType.MOUSE_POINT),
    PRESS_RIGHT_MOUSE_BUTTON("robot.action.pressMouseClick.right", ActionBootControllerType.MOUSE_POINT),

    RELEASE_LEFT_MOUSE_BUTTON("robot.action.releaseMouseClick.left", ActionBootControllerType.MOUSE_POINT),
    RELEASE_MIDDLE_MOUSE_BUTTON("robot.action.releaseMouseClick.middle", ActionBootControllerType.MOUSE_POINT),
    RELEASE_RIGHT_MOUSE_BUTTON("robot.action.releaseMouseClick.right", ActionBootControllerType.MOUSE_POINT);


    override fun validation(): Validationable? {
        return this.validation
    }

    override fun bundleName(): String {
        return this.bundleName
    }

    override fun controllerType(): ActionBootControllerType {
        return this.controllerType
    }

    override fun createController(): (RootSchemaGroupModel) -> AutomationController<*> {
        return { root: RootSchemaGroupModel -> MousePointActionController(root, root.getSelectedSchemaGroupModel(), this) }
    }

}
