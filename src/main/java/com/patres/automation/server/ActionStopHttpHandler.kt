package com.patres.automation.server

import com.patres.automation.action.RootSchemaGroupModel

class ActionStopHttpHandler : ActionHttpHandler("POST", "/action/stop/", "Stop") {

    override fun invokeAction(action: RootSchemaGroupModel) {
        action.stopAutomation()
    }

}