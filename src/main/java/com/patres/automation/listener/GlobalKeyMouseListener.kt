package com.patres.automation.listener

import org.jnativehook.GlobalScreen
import org.jnativehook.keyboard.NativeKeyListener
import org.jnativehook.mouse.NativeMouseListener
import org.jnativehook.mouse.NativeMouseWheelListener
import org.slf4j.LoggerFactory
import java.util.logging.Level
import java.util.logging.Logger

object GlobalKeyMouseListener {

    private val LOGGER = LoggerFactory.getLogger(GlobalKeyMouseListener::class.java)

    init {
        try {
            val logger = Logger.getLogger(GlobalScreen::class.java.getPackage().name)
            logger.level = Level.WARNING
            logger.useParentHandlers = false
            GlobalScreen.registerNativeHook()
        } catch (ex: Exception) {
            LOGGER.error("There was a problem registering the native hook.", ex)
        }
    }

    fun activeKeyListener(nativeKeyListener: NativeKeyListener) {
        GlobalScreen.addNativeKeyListener(nativeKeyListener)
    }

    fun deactivateKeyListener(nativeKeyListener: NativeKeyListener) {
        GlobalScreen.removeNativeKeyListener(nativeKeyListener)
    }

    fun activeMouseListener(listener: NativeMouseListener) {
        GlobalScreen.addNativeMouseListener(listener)
    }

    fun deactivateMouseListener(listener: NativeMouseListener) {
        GlobalScreen.removeNativeMouseListener(listener)
    }

    fun activeMouseWheelListener(listener: NativeMouseWheelListener) {
        GlobalScreen.addNativeMouseWheelListener(listener)
    }

    fun deactivateMouseWheelListener(listener: NativeMouseWheelListener) {
        GlobalScreen.removeNativeMouseWheelListener(listener)
    }

}