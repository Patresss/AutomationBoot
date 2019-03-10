package com.patres.automation.serialize

import com.patres.automation.action.TextFieldActionModel
import com.patres.automation.action.delay.DelayAction
import com.patres.automation.action.mouse.click.LeftMouseClickAction
import com.patres.automation.action.mouse.click.MiddleMouseClickAction
import com.patres.automation.action.mouse.click.RightMouseClickAction
import com.patres.automation.action.mouse.doubleClick.LeftDoubleMouseClickAction
import com.patres.automation.action.mouse.doubleClick.MiddleDoubleMouseClickAction
import com.patres.automation.action.mouse.doubleClick.RightDoubleMouseClickAction
import com.patres.automation.action.mouse.move.MoveMouseAction
import com.patres.automation.action.mouse.press.PressLeftMouseAction
import com.patres.automation.action.mouse.press.PressMiddleMouseAction
import com.patres.automation.action.mouse.press.PressRightMouseAction
import com.patres.automation.action.mouse.release.ReleaseLeftMouseAction
import com.patres.automation.action.mouse.release.ReleaseMiddleMouseAction
import com.patres.automation.action.mouse.release.ReleaseRightMouseAction
import com.patres.automation.action.mouse.wheel.ScrollWheelDownAction
import com.patres.automation.action.mouse.wheel.ScrollWheelUpAction
import com.patres.automation.action.text.PasteTextAction
import com.patres.automation.action.text.TypeTextAction
import com.patres.automation.excpetion.ApplicationException
import com.patres.automation.gui.controller.model.TextActionController
import com.patres.automation.menuItem.MenuItem
import com.patres.automation.model.RootSchemaGroupModel
import com.patres.automation.model.SchemaGroupModel
import com.patres.automation.serialize.model.TextActionSerialized


object TextFieldActionMapper : Mapper<TextFieldActionModel<out TextActionController>, TextActionSerialized> {

    private val actionInstanceMap = mapOf(
            MenuItem.DELAY.name to { root: RootSchemaGroupModel, parent: SchemaGroupModel -> DelayAction(root, parent) },
            MenuItem.MOVE_MOUSE.name to { root: RootSchemaGroupModel, parent: SchemaGroupModel -> MoveMouseAction(root, parent) },

            MenuItem.CLICK_LEFT_MOUSE_BUTTON.name to { root: RootSchemaGroupModel, parent: SchemaGroupModel -> LeftMouseClickAction(root, parent) },
            MenuItem.CLICK_MIDDLE_MOUSE_BUTTON.name to { root: RootSchemaGroupModel, parent: SchemaGroupModel -> MiddleMouseClickAction(root, parent) },
            MenuItem.CLICK_RIGHT_MOUSE_BUTTON.name to { root: RootSchemaGroupModel, parent: SchemaGroupModel -> RightMouseClickAction(root, parent) },

            MenuItem.DOUBLE_CLICK_LEFT_MOUSE_BUTTON.name to { root: RootSchemaGroupModel, parent: SchemaGroupModel -> LeftDoubleMouseClickAction(root, parent) },
            MenuItem.DOUBLE_CLICK_MIDDLE_MOUSE_BUTTON.name to { root: RootSchemaGroupModel, parent: SchemaGroupModel -> MiddleDoubleMouseClickAction(root, parent) },
            MenuItem.DOUBLE_CLICK_RIGHT_MOUSE_BUTTON.name to { root: RootSchemaGroupModel, parent: SchemaGroupModel -> RightDoubleMouseClickAction(root, parent) },

            MenuItem.PRESS_LEFT_MOUSE_BUTTON.name to { root: RootSchemaGroupModel, parent: SchemaGroupModel -> PressLeftMouseAction(root, parent) },
            MenuItem.PRESS_MIDDLE_MOUSE_BUTTON.name to { root: RootSchemaGroupModel, parent: SchemaGroupModel -> PressMiddleMouseAction(root, parent) },
            MenuItem.PRESS_RIGHT_MOUSE_BUTTON.name to { root: RootSchemaGroupModel, parent: SchemaGroupModel -> PressRightMouseAction(root, parent) },

            MenuItem.RELEASE_LEFT_MOUSE_BUTTON.name to { root: RootSchemaGroupModel, parent: SchemaGroupModel -> ReleaseLeftMouseAction(root, parent) },
            MenuItem.RELEASE_MIDDLE_MOUSE_BUTTON.name to { root: RootSchemaGroupModel, parent: SchemaGroupModel -> ReleaseMiddleMouseAction(root, parent) },
            MenuItem.RELEASE_RIGHT_MOUSE_BUTTON.name to { root: RootSchemaGroupModel, parent: SchemaGroupModel -> ReleaseRightMouseAction(root, parent) },
            MenuItem.SCROLL_WHEEL_UP.name to { root: RootSchemaGroupModel, parent: SchemaGroupModel -> ScrollWheelUpAction(root, parent) },
            MenuItem.SCROLL_WHEEL_DOWN.name to { root: RootSchemaGroupModel, parent: SchemaGroupModel -> ScrollWheelDownAction(root, parent) },

            MenuItem.PASTE_TEXT.name to { root: RootSchemaGroupModel, parent: SchemaGroupModel -> PasteTextAction(root, parent) },
            MenuItem.TYPE_TEXT.name to { root: RootSchemaGroupModel, parent: SchemaGroupModel -> TypeTextAction(root, parent) }
    )

    private val actionClassMap = mapOf(
            DelayAction::class.java to MenuItem.DELAY.name,
            MoveMouseAction::class.java to MenuItem.MOVE_MOUSE.name,

            LeftMouseClickAction::class.java to MenuItem.CLICK_LEFT_MOUSE_BUTTON.name,
            MiddleMouseClickAction::class.java to MenuItem.CLICK_MIDDLE_MOUSE_BUTTON.name,
            RightMouseClickAction::class.java to MenuItem.CLICK_RIGHT_MOUSE_BUTTON.name,

            LeftDoubleMouseClickAction::class.java to MenuItem.DOUBLE_CLICK_LEFT_MOUSE_BUTTON.name,
            MiddleDoubleMouseClickAction::class.java to MenuItem.DOUBLE_CLICK_MIDDLE_MOUSE_BUTTON.name,
            RightDoubleMouseClickAction::class.java to MenuItem.DOUBLE_CLICK_RIGHT_MOUSE_BUTTON.name,

            PressLeftMouseAction::class.java to MenuItem.PRESS_LEFT_MOUSE_BUTTON.name,
            PressMiddleMouseAction::class.java to MenuItem.PRESS_MIDDLE_MOUSE_BUTTON.name,
            PressRightMouseAction::class.java to MenuItem.PRESS_RIGHT_MOUSE_BUTTON.name,

            ReleaseLeftMouseAction::class.java to MenuItem.RELEASE_LEFT_MOUSE_BUTTON.name,
            ReleaseMiddleMouseAction::class.java to MenuItem.RELEASE_MIDDLE_MOUSE_BUTTON.name,
            ReleaseRightMouseAction::class.java to MenuItem.RELEASE_RIGHT_MOUSE_BUTTON.name,
            ScrollWheelUpAction::class.java to MenuItem.SCROLL_WHEEL_UP.name,
            ScrollWheelDownAction::class.java to MenuItem.SCROLL_WHEEL_DOWN.name,

            PasteTextAction::class.java to MenuItem.PASTE_TEXT.name,
            TypeTextAction::class.java to MenuItem.TYPE_TEXT.name
    )

    override fun modelToSerialize(model: TextFieldActionModel<out TextActionController>): TextActionSerialized {
        val actionName = actionClassMap[model.javaClass]
                ?: throw ApplicationException("Cannot find action name ${model.javaClass} to serialize")
        return TextActionSerialized(model.getActionValue(), actionName)
    }

    override fun serializedToModel(serializedModel: TextActionSerialized, root: RootSchemaGroupModel, parent: SchemaGroupModel): TextFieldActionModel<out TextActionController> {
        return actionInstanceMap[serializedModel.actionName]?.invoke(root, parent)?.apply { setActionValue(serializedModel.actionNodeValue) }
                ?: throw ApplicationException("Cannot find model ${serializedModel.actionName} to serialize")
    }

}


