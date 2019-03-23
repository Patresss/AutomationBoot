package com.patres.automation.serialize

import com.patres.automation.action.TextActionModel
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
import com.patres.automation.action.script.OpenFileOrDirectoryAction
import com.patres.automation.action.script.WindowsRunAndWaitScriptAction
import com.patres.automation.action.script.WindowsRunScriptAction
import com.patres.automation.action.text.paste.PasteTextFromFieldAction
import com.patres.automation.action.text.paste.PasteTextFromFileAction
import com.patres.automation.action.text.type.TypeTextFromFieldAction
import com.patres.automation.action.text.type.TypeTextFromFileAction
import com.patres.automation.excpetion.ApplicationException
import com.patres.automation.gui.controller.model.TextActionController
import com.patres.automation.menuItem.MenuItem
import com.patres.automation.model.RootSchemaGroupModel
import com.patres.automation.model.SchemaGroupModel
import com.patres.automation.serialize.model.TextActionSerialized


object TextFieldActionMapper : Mapper<TextActionModel<out TextActionController>, TextActionSerialized> {

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

            MenuItem.PASTE_TEXT.name to PasteTextFromFieldAction.createAction,
            MenuItem.PASTE_TEXT_FROM_FILE.name to PasteTextFromFileAction.createAction,
            MenuItem.TYPE_TEXT.name to TypeTextFromFieldAction.createAction,
            MenuItem.TYPE_TEXT_FROM_FILE.name to TypeTextFromFileAction.createAction,

            MenuItem.OPEN_FILE_OR_DIRECTORY.name to OpenFileOrDirectoryAction.createAction,
            MenuItem.WINDOWS_SCRIPT_RUN.name to WindowsRunScriptAction.createAction,
            MenuItem.WINDOWS_SCRIPT_RUN_AND_WAITE.name to WindowsRunAndWaitScriptAction.createAction
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

            PasteTextFromFieldAction::class.java to MenuItem.PASTE_TEXT.name,
            PasteTextFromFileAction::class.java to MenuItem.PASTE_TEXT_FROM_FILE.name,
            TypeTextFromFieldAction::class.java to MenuItem.TYPE_TEXT.name,
            TypeTextFromFileAction::class.java to MenuItem.TYPE_TEXT_FROM_FILE.name,

            OpenFileOrDirectoryAction::class.java to MenuItem.OPEN_FILE_OR_DIRECTORY.name,
            WindowsRunScriptAction::class.java to MenuItem.WINDOWS_SCRIPT_RUN.name,
            WindowsRunAndWaitScriptAction::class.java to MenuItem.WINDOWS_SCRIPT_RUN_AND_WAITE.name
    )

    override fun modelToSerialize(model: TextActionModel<out TextActionController>): TextActionSerialized {
        val actionName = actionClassMap[model.javaClass]
                ?: throw ApplicationException("Cannot find action name ${model.javaClass} to serialize")
        return TextActionSerialized(model.getActionValue(), actionName)
    }

    override fun serializedToModel(serializedModel: TextActionSerialized, root: RootSchemaGroupModel, parent: SchemaGroupModel): TextActionModel<out TextActionController> {
        return actionInstanceMap[serializedModel.actionName]?.invoke(root, parent)?.apply { setActionValue(serializedModel.actionNodeValue) }
                ?: throw ApplicationException("Cannot find model ${serializedModel.actionName} to serialize")
    }

}


