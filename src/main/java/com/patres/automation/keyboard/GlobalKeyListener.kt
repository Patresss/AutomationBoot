package com.patres.automation.keyboard

import com.patres.automation.Main
import org.jnativehook.GlobalScreen
import org.jnativehook.NativeHookException
import org.jnativehook.keyboard.NativeKeyEvent
import org.jnativehook.keyboard.NativeKeyListener
import org.slf4j.LoggerFactory
import java.util.logging.Level
import java.util.logging.Logger

object GlobalKeyListener : NativeKeyListener {

    private val LOGGER = LoggerFactory.getLogger(GlobalKeyListener::class.java)

    var isStop = false

    private val keyAdapter = KeyAdapter()

    private val pressedKeys = ArrayList<Int>()

    override fun nativeKeyPressed(e: NativeKeyEvent) {
        pressedKeys.add(keyAdapter.getKeyEvent(e))
        if (pressedKeys.containsAll(Main.globalSettings.stopKeys.map { it.keyValue })) {
            isStop = true
        }
    }

    override fun nativeKeyReleased(e: NativeKeyEvent) {
        pressedKeys.remove(keyAdapter.getKeyEvent(e))
    }

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

    fun reset() {
        isStop = false
        pressedKeys.clear()
    }

}