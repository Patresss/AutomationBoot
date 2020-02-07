package com.patres.automation.server

import com.patres.automation.ApplicationLauncher
import com.patres.automation.action.RootSchemaGroupModel
import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import java.net.HttpURLConnection

abstract class ActionHttpHandler(
        private val method: String,
        val url: String,
        private val invokedNameAction: String
) : HttpHandler {

    abstract fun invokeAction(action: RootSchemaGroupModel)

    override fun handle(exchange: HttpExchange) {
        if (method == exchange.requestMethod) {
            val actionName = exchange.requestURI.path.removePrefix(url)
            val action = ApplicationLauncher.mainController?.findActionByName(actionName)
            if (action != null) {
                invokeAction(action)
                createSuccessResponse(exchange, actionName)
            } else {
                createCannotFindActionResponse(exchange, actionName)
            }
        } else {
            createBadMethodResponse(exchange)
        }
        exchange.close()
    }

    private fun createSuccessResponse(exchange: HttpExchange, actionName: String) {
        val responseMessage = "$invokedNameAction action: $actionName"
        createResponse(exchange, responseMessage, HttpURLConnection.HTTP_OK)
    }

    private fun createCannotFindActionResponse(exchange: HttpExchange, actionName: String) {
        val responseMessage = "Cannot find action: $actionName"
        createResponse(exchange, responseMessage, HttpURLConnection.HTTP_BAD_REQUEST)
    }

    private fun createBadMethodResponse(exchange: HttpExchange) {
        val responseMessage = "Method is not allowed. Please use POST"
        createResponse(exchange, responseMessage, HttpURLConnection.HTTP_BAD_METHOD)
    }

    private fun createResponse(exchange: HttpExchange, responseMessage: String, statusCode: Int) {
        exchange.sendResponseHeaders(statusCode, responseMessage.toByteArray().size.toLong())
        exchange.responseBody.apply {
            write(responseMessage.toByteArray())
            flush()
        }
    }

}