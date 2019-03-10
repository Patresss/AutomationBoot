package com.patres.automation.keyboard

import org.jnativehook.GlobalScreen
import org.jnativehook.NativeHookException
import org.jnativehook.keyboard.NativeKeyEvent
import org.jnativehook.keyboard.NativeKeyListener
import org.slf4j.LoggerFactory

import java.util.logging.Level
import java.util.logging.Logger

object GlobalKeyListener : NativeKeyListener {

    private val LOGGER = LoggerFactory.getLogger(GlobalKeyListener::class.java)

    private const val STOP_KEY = NativeKeyEvent.VC_ESCAPE

    var isStop = false

    override fun nativeKeyPressed(e: NativeKeyEvent) {
        if (e.keyCode == STOP_KEY) {
            isStop = true
        }
    }

    override fun nativeKeyReleased(e: NativeKeyEvent) {}

    override fun nativeKeyTyped(e: NativeKeyEvent) {}

    fun activeListener() {
        try {
            val logger = Logger.getLogger(GlobalScreen::class.java.getPackage().name)
            logger.level = Level.WARNING
            logger.useParentHandlers = false
            GlobalScreen.registerNativeHook()
        } catch (ex: NativeHookException) {
            LOGGER.error("There was a problem registering the native hook.", ex)
        }
        GlobalScreen.addNativeKeyListener(this)
    }
}