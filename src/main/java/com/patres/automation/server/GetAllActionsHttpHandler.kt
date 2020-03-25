package com.patres.automation.server

import com.patres.automation.ApplicationLauncher
import com.sun.net.httpserver.HttpExchange
import org.slf4j.LoggerFactory
import java.net.HttpURLConnection
import java.net.Inet4Address


class GetAllActionsHttpHandler(
        private val method: String,
        url: String
) : ApplicationHttpHandler(url) {

    val ip: String = Inet4Address.getLocalHost().getHostAddress()


    companion object {
        val logger = LoggerFactory.getLogger(GetAllActionsHttpHandler::class.java)!!
    }

    override fun handle(exchange: HttpExchange) {
        logger.debug("Receive request - method: ${exchange.requestMethod}, url: ${exchange.requestURI.path}")
        if (method == exchange.requestMethod) {
            createResponse(exchange, createButtonsWithAction(), HttpURLConnection.HTTP_OK)
        } else {
            createBadMethodResponse(exchange)
        }
        logger.debug("Send response request - code: ${exchange.responseCode}, body: ${exchange.responseBody}") // todo repsonse as text
        exchange.close()
    }

    private fun createBadMethodResponse(exchange: HttpExchange) {
        val responseMessage = "Method is not allowed. Please use POST"
        createResponse(exchange, responseMessage, HttpURLConnection.HTTP_BAD_METHOD)
    }

    private fun createButtonsWithAction(): String {
        return ApplicationLauncher.mainController.findAllowedAction().joinToString("<br /> <br />") {
            """
                <button onclick="location.href='http://${ip}:${ApplicationLauncher.globalSettings.port}/action/run/${it.getEndpointName()}'" type="button">
                    ${it.getEndpointName()}
                </button>
            """.trimIndent()
        }
    }

    private fun createResponse(exchange: HttpExchange, responseMessage: String, statusCode: Int) {
        exchange.sendResponseHeaders(statusCode, responseMessage.toByteArray().size.toLong())
        exchange.responseBody.apply {
            write(responseMessage.toByteArray())
            flush()
        }
    }

}