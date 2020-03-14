package com.patres.automation.server

import com.sun.net.httpserver.HttpServer
import org.slf4j.LoggerFactory
import java.net.InetSocketAddress


object ServerBoot {

    val logger = LoggerFactory.getLogger(ServerBoot::class.java)!!
    const val DEFAULT_PORT = 8012
    const val MIN_PORT = 0
    const val MAX_PORT = 65535
    private val actionHandlers = setOf(
            ActionRunHttpHandler(),
            ActionStopHttpHandler(),
            GetAllActionsHttpHandler("GET", "/action/buttons"))

    fun run(port: Int = DEFAULT_PORT) {
        val server = HttpServer.create(InetSocketAddress(port), 0)
        actionHandlers.forEach { actionHandler ->
            server.createContext(actionHandler.url, actionHandler)
        }

        server.executor = null // creates a default executor
        logger.debug("Server is starting...")
        server.start()
    }

}