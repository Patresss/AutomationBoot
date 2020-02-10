package com.patres.automation.listener.record

import com.patres.automation.keyboard.KeyAdapter
import com.patres.automation.listener.GlobalKeyMouseListener
import org.jnativehook.keyboard.NativeKeyEvent
import org.jnativehook.keyboard.NativeKeyListener

class RecordKeyListener(
        private val actionRecorder: ActionRecorder
) : NativeKeyListener, RecordListener {

    override fun nativeKeyPressed(keyEvent: NativeKeyEvent) {
        actionRecorder.addPressKeyboardKey(KeyAdapter.getKeyEvent(keyEvent))
    }

    override fun nativeKeyReleased(keyEvent: NativeKeyEvent) {
    }

    override fun nativeKeyTyped(e: NativeKeyEvent) {}

    override fun activateListener() {
        GlobalKeyMouseListener.activeKeyListener(this)
    }

    override fun deactivateListener() {
        GlobalKeyMouseListener.deactivateKeyListener(this)
    }

}