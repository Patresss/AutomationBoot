package com.patres.automation.server

import com.patres.automation.ApplicationLauncher
import com.patres.automation.action.RootSchemaGroupModel
import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import org.slf4j.LoggerFactory
import java.net.HttpURLConnection

abstract class ApplicationHttpHandler(
        val url: String
) : HttpHandler