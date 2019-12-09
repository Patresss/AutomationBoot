package com.patres.automation

import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.slf4j.LoggerFactory


object ServerBoot {

    fun run() {
        embeddedServer(Netty, 8080) {
            routing {
                get("/action/run/{actionName}") {
                    val actionName = call.parameters["actionName"]
                    if (actionName != null) {
                        val action = Main.mainController?.findActionByName(actionName)
                        action?.runAutomation(false)
                        call.respondText("Run action: $actionName", ContentType.Text.Html)
                    } else {
                        call.respondText("Cannot find action: $actionName", ContentType.Text.Html)
                    }
                }
            }
        }.start(wait = false)
    }

}