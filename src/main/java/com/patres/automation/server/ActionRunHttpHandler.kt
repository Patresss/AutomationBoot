package com.patres.automation.server

import com.patres.automation.action.RootSchemaGroupModel

class ActionRunHttpHandler : ActionHttpHandler("GET", "/action/run/", "Run") { // TODO change GET to POST

    override fun invokeAction(action: RootSchemaGroupModel) {
        action.runAutomation()
    }

}