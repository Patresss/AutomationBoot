package com.patres.automation.serialize

import com.patres.automation.action.script.OpenFileOrDirectoryAction
import com.patres.automation.action.text.PasteTextFromFieldAction
import com.patres.automation.action.text.TypeTextFromFieldAction
import com.patres.automation.excpetion.ApplicationException
import com.patres.automation.gui.controller.model.TextActionController
import com.patres.automation.menuItem.MenuItem
import com.patres.automation.model.RootSchemaGroupModel
import com.patres.automation.model.SchemaGroupModel
import com.patres.automation.serialize.model.TextActionSerialized
//
//
//object TextFieldActionMapper : Mapper<TextActionModel<out TextActionController>, TextActionSerialized> {
//
//    private val actionInstanceMap = mapOf(
////            MenuItem.DELAY_ITEM.name to { root: RootSchemaGroupModel, parent: SchemaGroupModel -> {} },
////
////            MenuItem.SCROLL_WHEEL_UP.name to { root: RootSchemaGroupModel, parent: SchemaGroupModel -> {}  },
////            MenuItem.SCROLL_WHEEL_DOWN.name to { root: RootSchemaGroupModel, parent: SchemaGroupModel -> {}  },
////
////            MenuItem.PASTE_TEXT.name to PasteTextFromFieldAction.createAction,
////            MenuItem.PASTE_TEXT_FROM_FILE.name to PasteTextFromFileAction.createAction,
////            MenuItem.TYPE_TEXT.name to TypeTextFromFieldAction.createAction,
////            MenuItem.TYPE_TEXT_FROM_FILE.name to TypeTextFromFileAction.createAction,
////
////            MenuItem.OPEN_FILE_OR_DIRECTORY.name to OpenFileOrDirectoryAction.createAction,
////            MenuItem.WINDOWS_SCRIPT_RUN.name to WindowsRunScriptAction.createAction,
////            MenuItem.WINDOWS_SCRIPT_RUN_AND_WAITE.name to WindowsRunAndWaitScriptAction.createAction
//    )
//
//    private val actionClassMap = mapOf(
////            DelayActionOld::class.java to MenuItem.DELAY.name,
//
////            ScrollWheelUpAction::class.java to MenuItem.SCROLL_WHEEL_UP.name,
////            ScrollWheelDownAction::class.java to MenuItem.SCROLL_WHEEL_DOWN.name,
////
////            PasteTextFromFieldAction::class.java to MenuItem.PASTE_TEXT.name,
////            PasteTextFromFileAction::class.java to MenuItem.PASTE_TEXT_FROM_FILE.name,
////            TypeTextFromFieldAction::class.java to MenuItem.TYPE_TEXT.name,
////            TypeTextFromFileAction::class.java to MenuItem.TYPE_TEXT_FROM_FILE.name,
////
////            OpenFileOrDirectoryAction::class.java to MenuItem.OPEN_FILE_OR_DIRECTORY.name,
////            WindowsRunScriptAction::class.java to MenuItem.WINDOWS_SCRIPT_RUN.name,
////            WindowsRunAndWaitScriptAction::class.java to MenuItem.WINDOWS_SCRIPT_RUN_AND_WAITE.name
//    )
//
//    override fun modelToSerialize(model: TextActionModel<out TextActionController>): TextActionSerialized {
//        val actionName = actionClassMap[model.javaClass]
//                ?: throw ApplicationException("Cannot find action name ${model.javaClass} to serialize")
//        return TextActionSerialized(model.getActionValue(), actionName)
//    }
//
//    override fun serializedToModel(serializedModel: TextActionSerialized, root: RootSchemaGroupModel, parent: SchemaGroupModel): TextActionModel<out TextActionController> {
////        return actionInstanceMap[serializedModel.actionName]?.invoke(root, parent)?.apply { setActionValue(serializedModel.actionNodeValue) }
////                ?: throw ApplicationException("Cannot find model ${serializedModel.actionName} to serialize")
//            return TODO()
//    }
//
//}
//
//
