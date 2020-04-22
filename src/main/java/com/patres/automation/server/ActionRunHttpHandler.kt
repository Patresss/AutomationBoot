package com.patres.automation.server

import com.patres.automation.action.RootSchemaGroupModel

class ActionRunHttpHandler : ActionHttpHandler("POST", "/action/run/", "Run") {

    override fun invokeAction(action: RootSchemaGroupModel) {
        action.runAutomation()
    }

}