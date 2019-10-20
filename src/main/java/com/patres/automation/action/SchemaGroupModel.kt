package com.patres.automation.action

import com.patres.automation.type.ActionBootSchema
import javafx.beans.property.BooleanProperty

class SchemaGroupModel(
        private val actions: List<AbstractAction>,
        private val iteration: Int,
        private val automationRunningProperty: BooleanProperty?
) : AbstractAction(ActionBootSchema.ADD_GROUP) {

    override fun runAction() {
        for (i in 0 until iteration) {
            for (action in actions) {
                if (automationRunningProperty?.get() != false) {
                    action.runAction()
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


}