package com.patres.automation.menuItem

import com.patres.automation.action.delay.DelayAction
import com.patres.automation.action.keyboard.PressKeyboardButtonAction
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
import com.patres.automation.model.RootSchemaGroupModel

object MenuItemHandlers {


    val runAutomation: (rootSchemaGroupModel: RootSchemaGroupModel) -> Unit = { it.runAutomation() }

    val moveToDown: (rootSchemaGroupModel: RootSchemaGroupModel) -> Unit = { it.selectedModel?.downActionBlock() }

    val moveToUp: (rootSchemaGroupModel: RootSchemaGroupModel) -> Unit = { it.selectedModel?.upActionBlock() }

    val remove: (rootSchemaGroupModel: RootSchemaGroupModel) -> Unit = { it.removeSelectedModel() }

    val addSchemeGroup: (rootSchemaGroupModel: RootSchemaGroupModel) -> Unit = { it.addSchemaGroup() }

    val addLeftClickMouse: (_: RootSchemaGroupModel) -> Unit = { it.addNodeAction(LeftMouseClickAction(it, it.getSelectedSchemaGroupModel())) }

    val addMiddleClickMouse: (_: RootSchemaGroupModel) -> Unit = { it.addNodeAction(MiddleMouseClickAction(it, it.getSelectedSchemaGroupModel())) }

    val addRightClickMouse: (_: RootSchemaGroupModel) -> Unit = { it.addNodeAction(RightMouseClickAction(it, it.getSelectedSchemaGroupModel())) }

    val addDoubleLeftClickMouse: (_: RootSchemaGroupModel) -> Unit = { it.addNodeAction(LeftDoubleMouseClickAction(it, it.getSelectedSchemaGroupModel())) }

    val addDoubleMiddleClickMouse: (_: RootSchemaGroupModel) -> Unit = { it.addNodeAction(MiddleDoubleMouseClickAction(it, it.getSelectedSchemaGroupModel())) }

    val addDoubleRightClickMouse: (_: RootSchemaGroupModel) -> Unit = { it.addNodeAction(RightDoubleMouseClickAction(it, it.getSelectedSchemaGroupModel())) }

    val addReleaseLeftMouse: (_: RootSchemaGroupModel) -> Unit = { it.addNodeAction(ReleaseLeftMouseAction(it, it.getSelectedSchemaGroupModel())) }

    val addReleaseMiddlesMouse: (_: RootSchemaGroupModel) -> Unit = { it.addNodeAction(ReleaseMiddleMouseAction(it, it.getSelectedSchemaGroupModel())) }

    val addReleaseRightoMouse: (_: RootSchemaGroupModel) -> Unit = { it.addNodeAction(ReleaseRightMouseAction(it, it.getSelectedSchemaGroupModel())) }

    val addPressLeftMouse: (_: RootSchemaGroupModel) -> Unit = { it.addNodeAction(PressLeftMouseAction(it, it.getSelectedSchemaGroupModel())) }

    val addPressMiddleMouse: (_: RootSchemaGroupModel) -> Unit = { it.addNodeAction(PressMiddleMouseAction(it, it.getSelectedSchemaGroupModel())) }

    val addPressRightMouse: (_: RootSchemaGroupModel) -> Unit = { it.addNodeAction(PressRightMouseAction(it, it.getSelectedSchemaGroupModel())) }

    val addMoveMouse: (_: RootSchemaGroupModel) -> Unit = { it.addNodeAction(MoveMouseAction(it, it.getSelectedSchemaGroupModel())) }

    val addDelay: (_: RootSchemaGroupModel) -> Unit = { it.addNodeAction(DelayAction(it, it.getSelectedSchemaGroupModel())) }

    val addScrollWheelUp: (_: RootSchemaGroupModel) -> Unit = { it.addNodeAction(ScrollWheelUpAction(it, it.getSelectedSchemaGroupModel())) }

    val addScrollWheelDown: (_: RootSchemaGroupModel) -> Unit = { it.addNodeAction(ScrollWheelDownAction(it, it.getSelectedSchemaGroupModel())) }

    val addPasteText: (_: RootSchemaGroupModel) -> Unit = { it.addNodeAction(PasteTextAction(it, it.getSelectedSchemaGroupModel())) }

    val addTypeText: (_: RootSchemaGroupModel) -> Unit = { it.addNodeAction(TypeTextAction(it, it.getSelectedSchemaGroupModel())) }

    val addPressKeyboardButton: (_: RootSchemaGroupModel) -> Unit = { it.addNodeAction(PressKeyboardButtonAction(it, it.getSelectedSchemaGroupModel())) }

}