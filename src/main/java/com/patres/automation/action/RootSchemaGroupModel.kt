package com.patres.automation.action

class RootSchemaGroupModel(
        val schemaGroupModel: SchemaGroupModel,
        val actionRunner: ActionRunner
) {

    fun runAutomation() {
        actionRunner.runAutomation(schemaGroupModel)
    }

    fun stopAutomation() {
        actionRunner.stopAutomation()
    }

}