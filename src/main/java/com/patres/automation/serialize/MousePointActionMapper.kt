package com.patres.automation.serialize

import com.patres.automation.action.mouse.MousePointAction
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
import com.patres.automation.excpetion.ApplicationException
import com.patres.automation.menuItem.MenuItem
import com.patres.automation.model.RootSchemaGroupModel
import com.patres.automation.model.SchemaGroupModel
import com.patres.automation.serialize.model.MousePointActionSerialized
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.util.*


object MousePointActionMapper : Mapper<MousePointAction, MousePointActionSerialized> {

    private val actionInstanceMap = mapOf(
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
            MenuItem.RELEASE_RIGHT_MOUSE_BUTTON.name to { root: RootSchemaGroupModel, parent: SchemaGroupModel -> ReleaseRightMouseAction(root, parent) }
    )

    private val actionClassMap = mapOf(
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
            ReleaseRightMouseAction::class.java to MenuItem.RELEASE_RIGHT_MOUSE_BUTTON.name
    )

    override fun modelToSerialize(model: MousePointAction): MousePointActionSerialized {
        val actionName = actionClassMap[model.javaClass]
                ?: throw ApplicationException("Cannot find action name ${model.javaClass} to serialize")
        return MousePointActionSerialized(model.getActionValue(), bytesArrayToBase64(model.controller.calculateImageBytesArray()), actionName)
    }

    override fun serializedToModel(serializedModel: MousePointActionSerialized, root: RootSchemaGroupModel, parent: SchemaGroupModel): MousePointAction {
        return actionInstanceMap[serializedModel.actionName]?.invoke(root, parent)?.apply {
            setActionValue(serializedModel.actionNodeValue)
            serializedModel.image?.let { setImageInputStream(base64ToInputStream(it)) }
        }
                ?: throw ApplicationException("Cannot find model ${serializedModel.actionName} to serialize")
    }

    private fun bytesArrayToBase64(bytes: ByteArray?): String? {
        bytes?.let {
            return Base64.getEncoder().encodeToString(bytes)
        }
        return null
    }

    private fun base64ToInputStream(string: String): InputStream {
        return ByteArrayInputStream(Base64.getDecoder().decode(string))
    }

}


