package com.patres.automation.keyboard.listener

import org.jnativehook.GlobalScreen
import org.jnativehook.NativeHookException
import org.jnativehook.keyboard.NativeKeyListener
import org.slf4j.LoggerFactory
import java.util.logging.Level
import java.util.logging.Logger

object GlobalKeyListener {

    private val LOGGER = LoggerFactory.getLogger(GlobalKeyListener::class.java)

    fun activeListener(nativeKeyListener: NativeKeyListener) {
        try {
            val logger = Logger.getLogger(GlobalScreen::class.java.getPackage().name)
            logger.level = Level.WARNING
            logger.useParentHandlers = false
            GlobalScreen.registerNativeHook()
        } catch (ex: NativeHookException) {
            LOGGER.error("There was a problem registering the native hook.", ex)
        }
        GlobalScreen.addNativeKeyListener(nativeKeyListener)
    }


}