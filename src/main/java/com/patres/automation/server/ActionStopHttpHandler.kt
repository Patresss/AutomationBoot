package com.patres.automation.server

import com.patres.automation.action.RootSchemaGroupModel

class ActionStopHttpHandler : ActionHttpHandler("GET", "/action/stop/", "Stop") {// TODO change GET to POST

    override fun invokeAction(action: RootSchemaGroupModel) {
        action.stopAutomation()
    }

}