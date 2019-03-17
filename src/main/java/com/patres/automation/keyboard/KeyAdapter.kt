package com.patres.automation.keyboard

import org.jnativehook.keyboard.NativeKeyEvent
import org.jnativehook.keyboard.SwingKeyAdapter

object KeyAdapter : SwingKeyAdapter() {

    fun getKeyEvent(nativeEvent: NativeKeyEvent): Int {
        return getJavaKeyEvent(nativeEvent)?.keyCode?: 0
    }

}