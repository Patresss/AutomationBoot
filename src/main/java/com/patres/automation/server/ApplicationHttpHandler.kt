package com.patres.automation.server

import com.sun.net.httpserver.HttpHandler

abstract class ApplicationHttpHandler(
        val url: String
) : HttpHandler