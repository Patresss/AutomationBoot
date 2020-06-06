package com.patres.automation.action.script

import com.patres.automation.action.AbstractAction
import com.patres.automation.action.SchemaGroupModel
import com.patres.automation.gui.controller.box.AbstractBox
import com.patres.automation.gui.controller.model.AutomationController
import com.patres.automation.type.ActionBootBrowser

class RunExistingSchemaAction(
        private val schema: SchemaGroupModel,
        actionBoot: ActionBootBrowser,
        box: AbstractBox<*>?
) : AbstractAction(actionBoot, box) {

    override fun runAction() {
        schema.runAction()
    }

    override fun validate() {
//        actionBoot.validation()?.check(path)
    }

    override fun toStringLog() = "Action: `$actionBootType` | schema: `$schema`"

}