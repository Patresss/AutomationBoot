package com.patres.automation

import com.patres.automation.file.TmpFileLoader
import javafx.application.Application
import org.slf4j.LoggerFactory


class Main {

    companion object {
        private val logger = LoggerFactory.getLogger(TmpFileLoader::class.java)

        @JvmStatic
        fun main(args: Array<String>) {
            logger.info("Application is starting...")
            Application.launch(ApplicationLauncher::class.java, *args)
        }

    }


}
