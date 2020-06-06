package com.patres.automation.action

import com.patres.automation.ApplicationLauncher
import com.patres.automation.gui.controller.box.AbstractBox
import com.patres.automation.gui.controller.box.SchemaGroupController
import com.patres.automation.gui.controller.model.AutomationController
import com.patres.automation.type.ActionBootSchema
import javafx.beans.property.BooleanProperty
import kotlin.system.measureNanoTime

class SchemaGroupModel(
        val actions: List<AbstractAction>,
        val iteration: Int,
        val automationRunningProperty: BooleanProperty,
        box: AbstractBox<*>?
) : AbstractAction(ActionBootSchema.SCHEMA_GROUP, box) {

    override fun runAction() {
        for (i in 0 until iteration) {
            for (action in actions) {
                if (automationRunningProperty.get()) {
                    val currentActionRunningPane = action.box?.currentActionRunningPane
                    currentActionRunningPane?.isVisible = true

                    action.runAndLogAction()
                    Thread.sleep(ApplicationLauncher.globalSettings.additionalDelayBetweenActions.calculateMilliseconds())

                    currentActionRunningPane?.isVisible = false
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