package com.patres.automation.action

import com.patres.automation.type.ActionBootSchema
import javafx.beans.property.BooleanProperty
import org.slf4j.LoggerFactory

class SchemaGroupModel(
        private val actions: List<AbstractAction>,
        private val iteration: Int,
        private val automationRunningProperty: BooleanProperty?
) : AbstractAction(ActionBootSchema.ADD_GROUP) {

    companion object {
        private val logger = LoggerFactory.getLogger(SchemaGroupModel::class.java)
    }

    override fun runAction() {
        for (i in 0 until iteration) {
            for (action in actions) {
                logger.info("Running action  - {}", action.toStringLog())
                if (automationRunningProperty?.get() != false) {
                    action.runAction()
                } else {
                    return
                }
                logger.info("Finished action - {}", action.toStringLog())
            }
        }
    }

    override fun validate() {
        for (action in actions) {
            action.validate()
        }
    }

    override fun toStringLog() = "Action: `$actionBoot` | iteration: `$iteration`"

}