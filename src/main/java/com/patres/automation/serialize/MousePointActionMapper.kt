package com.patres.automation.serialize

import com.patres.automation.action.mouse.MousePointActionOld
import com.patres.automation.action.mouse.MiddleMouseClickAction
import com.patres.automation.action.mouse.RightMouseClickAction
import com.patres.automation.action.mouse.LeftDoubleMouseClickAction
import com.patres.automation.action.mouse.MiddleDoubleMouseClickAction
import com.patres.automation.action.mouse.RightDoubleMouseClickAction
import com.patres.automation.action.mouse.MoveMouseAction
import com.patres.automation.action.mouse.PressLeftMouseAction
import com.patres.automation.action.mouse.PressMiddleMouseAction
import com.patres.automation.action.mouse.PressRightMouseAction
import com.patres.automation.action.mouse.ReleaseLeftMouseAction
import com.patres.automation.action.mouse.ReleaseMiddleMouseAction
import com.patres.automation.action.mouse.ReleaseRightMouseAction
import com.patres.automation.menuItem.MenuItem
import com.patres.automation.model.RootSchemaGroupModel
import com.patres.automation.model.SchemaGroupModel
import com.patres.automation.serialize.model.MousePointActionSerialized
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.util.*


object MousePointActionMapper : Mapper<MousePointActionOld, MousePointActionSerialized> {

    private val actionInstanceMap = mapOf(
            MenuItem.MOVE_MOUSE.name to { root: RootSchemaGroupModel, parent: SchemaGroupModel -> {} },

            MenuItem.CLICK_LEFT_MOUSE_BUTTON.name to { root: RootSchemaGroupModel, parent: SchemaGroupModel -> {} },
            MenuItem.CLICK_MIDDLE_MOUSE_BUTTON.name to { root: RootSchemaGroupModel, parent: SchemaGroupModel ->  {} },
            MenuItem.CLICK_RIGHT_MOUSE_BUTTON.name to { root: RootSchemaGroupModel, parent: SchemaGroupModel -> {} },

            MenuItem.DOUBLE_CLICK_LEFT_MOUSE_BUTTON.name to { root: RootSchemaGroupModel, parent: SchemaGroupModel ->  {} },
            MenuItem.DOUBLE_CLICK_MIDDLE_MOUSE_BUTTON.name to { root: RootSchemaGroupModel, parent: SchemaGroupModel -> {} },
            MenuItem.DOUBLE_CLICK_RIGHT_MOUSE_BUTTON.name to { root: RootSchemaGroupModel, parent: SchemaGroupModel ->  {} },

            MenuItem.PRESS_LEFT_MOUSE_BUTTON.name to { root: RootSchemaGroupModel, parent: SchemaGroupModel ->  {} },
            MenuItem.PRESS_MIDDLE_MOUSE_BUTTON.name to { root: RootSchemaGroupModel, parent: SchemaGroupModel ->  {} },
            MenuItem.PRESS_RIGHT_MOUSE_BUTTON.name to { root: RootSchemaGroupModel, parent: SchemaGroupModel ->  {} },

            MenuItem.RELEASE_LEFT_MOUSE_BUTTON.name to { root: RootSchemaGroupModel, parent: SchemaGroupModel ->  {} },
            MenuItem.RELEASE_MIDDLE_MOUSE_BUTTON.name to { root: RootSchemaGroupModel, parent: SchemaGroupModel ->  {} },
            MenuItem.RELEASE_RIGHT_MOUSE_BUTTON.name to { root: RootSchemaGroupModel, parent: SchemaGroupModel -> {} }
    )

    private val actionClassMap = mapOf(
            MoveMouseAction::class.java to MenuItem.MOVE_MOUSE.name,

//            LeftMouseClickAction::class.java to MenuItem.CLICK_LEFT_MOUSE_BUTTON.name,
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

    override fun modelToSerialize(model: MousePointActionOld): MousePointActionSerialized {
//        val actionName = actionClassMap[model.javaClass]
//                ?: throw ApplicationException("Cannot find action name ${model.javaClass} to serialize")
//        return MousePointActionSerialized(model.getActionValue(), bytesArrayToBase64(model.controller.calculateImageBytesArray()), model.controller.thresholdSlider.value, actionName)
        return TODO()

    }

    override fun serializedToModel(serializedModel: MousePointActionSerialized, root: RootSchemaGroupModel, parent: SchemaGroupModel): MousePointActionOld {
//        return actionInstanceMap[serializedModel.actionName]?.invoke(root, parent)?.apply {
//            setActionValue(serializedModel.actionNodeValue)
//            serializedModel.image?.let { setImageInputStream(base64ToInputStream(it)) }
//            controller.thresholdSlider.value = serializedModel.threshold?: 90.0
//        }
//                ?: throw ApplicationException("Cannot find model ${serializedModel.actionName} to serialize")
        return TODO()

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


