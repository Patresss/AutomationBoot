package com.patres.automation.server

import com.patres.automation.ApplicationLauncher
import com.patres.automation.settings.LanguageManager
import com.sun.net.httpserver.HttpExchange
import org.slf4j.LoggerFactory
import java.net.HttpURLConnection
import java.net.Inet4Address


class GetAllActionsHttpHandler(
        private val method: String,
        url: String
) : ApplicationHttpHandler(url) {

    val ip: String = Inet4Address.getLocalHost().hostAddress

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

    private fun createResponse(exchange: HttpExchange, responseMessage: String, statusCode: Int) {
        exchange.sendResponseHeaders(statusCode, responseMessage.toByteArray().size.toLong())
        exchange.responseBody.apply {
            write(responseMessage.toByteArray())
            flush()
        }
    }

    private fun createButtonsWithAction(): String {
        return """
            <!DOCTYPE html>
            <head>
            	<title>${LanguageManager.getLanguageString("application.name")}</title>
                <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500&display=swap" rel="stylesheet">
                <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
                <script>
                function reload() {
                    setTimeout(function () {
                        window.location.reload();
                    }, 3000);
                }
                </script>
            	<style>
            
                    * {
                        margin: 0;
                    }
                    
                    body {
                        font-family: 'Roboto', sans-serif;
                        color: red;
                        font-size: 10px;
                    }
                    
            		label {
                        color: #01547a;
                        font-family: 'Roboto', sans-serif;
                        font-size: 25px;
                        vertical-align: middle;
            		}
            		
            		h1 {
                        background-color: #0288D1;
                        color: white;
            			font-size: 30px;
            			font-family: 'Roboto', sans-serif;
                        padding: 20px;
            		}
            		
            		p {
            			color: white;
            			font-family: "Source Code Pro", Menlo, Monaco, fixed-width;
            		}
            
                    #action-container {
                        border: 1px solid #01547a;
                        padding: 10px;
                        border-radius: 5px;
                        margin: 15px;
                    }
                    
                    button {
                        border-radius: 50%;
                        cursor: pointer;
                        background: white;
                        border: 0;
                        font-size: 30px;
                        vertical-align: middle;
                    }
                    
                    i {
                        color: #0288D1;
                    }
                    
                    iframe {
                        color: white;
                        font-family: "Source Code Pro", Menlo, Monaco, fixed-width;
                        border: none;
                        margin: 10px 0px 0px 10px;
                    }
            	</style>
            </head>
            <body>
                <h1>${LanguageManager.getLanguageString("application.name")}</h1>

                <iframe frameBorder="0" width="100%" height="35" name="iframe" id="iframe" ></iframe>

                <div id="action-container">
                    """.trimIndent() +
                ApplicationLauncher.mainController.findAllowedAction().sortedBy { it.getEndpointName().toLowerCase() }.joinToString("<br/>") {
                    val action = if (it.automationRunningProperty.get()) "stop" else "run"
                    val icon = if ("run" == action) "fa-play-circle" else "fa-stop-circle"
                    """
                        <form onSubmit="reload()" action="http://${ip}:${ApplicationLauncher.globalSettings.port}/action/$action/${it.getEndpointName()}" method="POST" target="iframe">
                          <div>
                              <button>
                               <i class="fa $icon"></i>
                              </button>
                              <label>${it.getName()}</label>
                          </div>
                        </form>
                    """.trimIndent()
                } +
                """
                </div>
            </body>
            </html>
        """.trimIndent()
    }


}