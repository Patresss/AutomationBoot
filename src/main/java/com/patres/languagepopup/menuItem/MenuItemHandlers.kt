package com.patres.languagepopup.menuItem

import com.patres.languagepopup.action.mouse.click.LeftMouseClickAction
import com.patres.languagepopup.action.mouse.click.MiddleMouseClickAction
import com.patres.languagepopup.action.mouse.click.RightMouseClickAction
import com.patres.languagepopup.action.mouse.doubleClick.LeftDoubleMouseClickAction
import com.patres.languagepopup.action.mouse.doubleClick.MiddleDoubleMouseClickAction
import com.patres.languagepopup.action.mouse.doubleClick.RightDoubleMouseClickAction
import com.patres.languagepopup.action.mouse.press.PressLeftMouseAction
import com.patres.languagepopup.action.mouse.press.PressMiddleMouseAction
import com.patres.languagepopup.action.mouse.press.PressRightMouseAction
import com.patres.languagepopup.action.mouse.release.ReleaseLeftMouseAction
import com.patres.languagepopup.action.mouse.release.ReleaseMiddleMouseAction
import com.patres.languagepopup.action.mouse.release.ReleaseRightMouseAction
import com.patres.languagepopup.model.RootSchemaGroupModel

object MenuItemHandlers {


    val runAutomation: (rootSchemaGroupModel: RootSchemaGroupModel) -> Unit = { it.runAutomation() }

    val moveToDown: (rootSchemaGroupModel: RootSchemaGroupModel) -> Unit = { it.selectedModel?.downActionBlock() }

    val moveToUp: (rootSchemaGroupModel: RootSchemaGroupModel) -> Unit = { it.selectedModel?.upActionBlock() }

    val remove: (rootSchemaGroupModel: RootSchemaGroupModel) -> Unit = { it.removeSelectedModel() }

    val addSchemeGroup: (rootSchemaGroupModel: RootSchemaGroupModel) -> Unit = { it.addSchemaGroup() }

    val addLeftClickMouse: (_: RootSchemaGroupModel) -> Unit = { it.addTextAction(LeftMouseClickAction(it, it.getSelectedShemaGroupModel())) }

    val addMiddleClickMouse: (_: RootSchemaGroupModel) -> Unit = { it.addTextAction(MiddleMouseClickAction(it, it.getSelectedShemaGroupModel())) }

    val addRightClickMouse: (_: RootSchemaGroupModel) -> Unit = { it.addTextAction(RightMouseClickAction(it, it.getSelectedShemaGroupModel())) }

    val addDoubleLeftClickMouse: (_: RootSchemaGroupModel) -> Unit = { it.addTextAction(LeftDoubleMouseClickAction(it, it.getSelectedShemaGroupModel())) }

    val addDoubleMiddleClickMouse: (_: RootSchemaGroupModel) -> Unit = { it.addTextAction(MiddleDoubleMouseClickAction(it, it.getSelectedShemaGroupModel())) }

    val addDoubleRightClickMouse: (_: RootSchemaGroupModel) -> Unit = { it.addTextAction(RightDoubleMouseClickAction(it, it.getSelectedShemaGroupModel())) }

    val addReleaseLeftMouse: (_: RootSchemaGroupModel) -> Unit = { it.addTextAction(ReleaseLeftMouseAction(it, it.getSelectedShemaGroupModel())) }

    val addReleaseMiddlesMouse: (_: RootSchemaGroupModel) -> Unit = { it.addTextAction(ReleaseMiddleMouseAction(it, it.getSelectedShemaGroupModel())) }

    val addReleaseRightoMouse: (_: RootSchemaGroupModel) -> Unit = { it.addTextAction(ReleaseRightMouseAction(it, it.getSelectedShemaGroupModel())) }

    val addPressLeftMouse: (_: RootSchemaGroupModel) -> Unit = { it.addTextAction(PressLeftMouseAction(it, it.getSelectedShemaGroupModel())) }

    val addPressMiddleMouse: (_: RootSchemaGroupModel) -> Unit = { it.addTextAction(PressMiddleMouseAction(it, it.getSelectedShemaGroupModel())) }

    val addPressRightMouse: (_: RootSchemaGroupModel) -> Unit = { it.addTextAction(PressRightMouseAction(it, it.getSelectedShemaGroupModel())) }


}