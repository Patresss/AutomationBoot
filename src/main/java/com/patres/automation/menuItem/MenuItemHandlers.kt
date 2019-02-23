package com.patres.automation.menuItem

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
import com.patres.automation.model.RootSchemaGroupModel

object MenuItemHandlers {


    val runAutomation: (rootSchemaGroupModel: RootSchemaGroupModel) -> Unit = { it.runAutomation() }

    val moveToDown: (rootSchemaGroupModel: RootSchemaGroupModel) -> Unit = { it.selectedModel?.downActionBlock() }

    val moveToUp: (rootSchemaGroupModel: RootSchemaGroupModel) -> Unit = { it.selectedModel?.upActionBlock() }

    val remove: (rootSchemaGroupModel: RootSchemaGroupModel) -> Unit = { it.removeSelectedModel() }

    val addSchemeGroup: (rootSchemaGroupModel: RootSchemaGroupModel) -> Unit = { it.addSchemaGroup() }

    val addLeftClickMouse: (_: RootSchemaGroupModel) -> Unit = { it.addNodeAction(LeftMouseClickAction(it, it.getSelectedShemaGroupModel())) }

    val addMiddleClickMouse: (_: RootSchemaGroupModel) -> Unit = { it.addNodeAction(MiddleMouseClickAction(it, it.getSelectedShemaGroupModel())) }

    val addRightClickMouse: (_: RootSchemaGroupModel) -> Unit = { it.addNodeAction(RightMouseClickAction(it, it.getSelectedShemaGroupModel())) }

    val addDoubleLeftClickMouse: (_: RootSchemaGroupModel) -> Unit = { it.addNodeAction(LeftDoubleMouseClickAction(it, it.getSelectedShemaGroupModel())) }

    val addDoubleMiddleClickMouse: (_: RootSchemaGroupModel) -> Unit = { it.addNodeAction(MiddleDoubleMouseClickAction(it, it.getSelectedShemaGroupModel())) }

    val addDoubleRightClickMouse: (_: RootSchemaGroupModel) -> Unit = { it.addNodeAction(RightDoubleMouseClickAction(it, it.getSelectedShemaGroupModel())) }

    val addReleaseLeftMouse: (_: RootSchemaGroupModel) -> Unit = { it.addNodeAction(ReleaseLeftMouseAction(it, it.getSelectedShemaGroupModel())) }

    val addReleaseMiddlesMouse: (_: RootSchemaGroupModel) -> Unit = { it.addNodeAction(ReleaseMiddleMouseAction(it, it.getSelectedShemaGroupModel())) }

    val addReleaseRightoMouse: (_: RootSchemaGroupModel) -> Unit = { it.addNodeAction(ReleaseRightMouseAction(it, it.getSelectedShemaGroupModel())) }

    val addPressLeftMouse: (_: RootSchemaGroupModel) -> Unit = { it.addNodeAction(PressLeftMouseAction(it, it.getSelectedShemaGroupModel())) }

    val addPressMiddleMouse: (_: RootSchemaGroupModel) -> Unit = { it.addNodeAction(PressMiddleMouseAction(it, it.getSelectedShemaGroupModel())) }

    val addPressRightMouse: (_: RootSchemaGroupModel) -> Unit = { it.addNodeAction(PressRightMouseAction(it, it.getSelectedShemaGroupModel())) }

    val addMoveMouse: (_: RootSchemaGroupModel) -> Unit = { it.addNodeAction(MoveMouseAction(it, it.getSelectedShemaGroupModel())) }

    val addDelay: (_: RootSchemaGroupModel) -> Unit = { it.addNodeAction(DelayAction(it, it.getSelectedShemaGroupModel())) }


}