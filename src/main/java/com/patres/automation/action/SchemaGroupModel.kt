package com.patres.automation.action

import com.patres.automation.ApplicationLauncher
import com.patres.automation.type.ActionBootSchema
import javafx.application.Application
import javafx.beans.property.BooleanProperty

class SchemaGroupModel(
        val actions: List<AbstractAction>,
        val iteration: Int,
        val automationRunningProperty: BooleanProperty?
) : AbstractAction(ActionBootSchema.SCHEMA_GROUP) {

    override fun runAction() {
        for (i in 0 until iteration) {
            for (action in actions) {
                if (automationRunningProperty?.get() != false) {
                    action.runAndLogAction()
                    Thread.sleep(ApplicationLauncher.globalSettings.additionalDelayBetweenActions.calculateMilliseconds())
                } else {
                    return
                }
            }
        }
    }

    override fun validate() {
        for (action in actions) {
            action.validate()
        }
    }

    override fun toStringLog() = "Action: `$actionBootType` | iteration: `$iteration` | number of actions: `${actions.size}"

}