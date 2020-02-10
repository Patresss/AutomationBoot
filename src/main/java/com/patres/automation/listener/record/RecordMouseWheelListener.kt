package com.patres.automation.listener.record

import com.patres.automation.listener.GlobalKeyMouseListener
import org.jnativehook.mouse.NativeMouseWheelEvent
import org.jnativehook.mouse.NativeMouseWheelListener

class RecordMouseWheelListener(
        private val actionRecorder: ActionRecorder
) : NativeMouseWheelListener, RecordListener {

    override fun activateListener() {
        GlobalKeyMouseListener.activeMouseWheelListener(this)
    }

    override fun deactivateListener() {
        GlobalKeyMouseListener.deactivateMouseWheelListener(this)
    }

    override fun nativeMouseWheelMoved(nativeEvent: NativeMouseWheelEvent) {
        actionRecorder.addMouseScroll(nativeEvent.wheelRotation)
    }

}