package com.patres.automation.keyboard

import org.jnativehook.keyboard.NativeKeyEvent
import org.jnativehook.keyboard.SwingKeyAdapter

class KeyAdapter : SwingKeyAdapter() {

    fun getKeyEvent(nativeEvent: NativeKeyEvent): Int {
        return getJavaKeyEvent(nativeEvent)?.keyCode?: 0
    }

}