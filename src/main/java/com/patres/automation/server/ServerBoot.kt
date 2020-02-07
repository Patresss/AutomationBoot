package com.patres.automation.server

import com.patres.automation.ApplicationLauncher
import com.sun.net.httpserver.HttpServer
import org.slf4j.LoggerFactory
import java.net.InetSocketAddress


object ServerBoot {

    val logger = LoggerFactory.getLogger(ApplicationLauncher::class.java)!!
    private val actionHandlers = setOf(ActionRunHttpHandler(), ActionStopHttpHandler())

    fun run() {
        val serverPort = ApplicationLauncher.globalSettings.port
        val server = HttpServer.create(InetSocketAddress(serverPort), 0)
        actionHandlers.forEach { actionHandler ->
            server.createContext(actionHandler.url, actionHandler)
        }

        server.executor = null // creates a default executor
        logger.debug("Server is starting...")
        server.start()
    }

}