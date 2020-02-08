package com.patres.automation.record

import com.patres.automation.keyboard.listener.GlobalKeyMouseListener
import com.patres.automation.point.Point
import org.jnativehook.mouse.NativeMouseEvent
import org.jnativehook.mouse.NativeMouseListener

class RecordMouseListener(
        private val actionRecorder: ActionRecorder
) : NativeMouseListener, RecordListener {

    override fun activateListener() {
        GlobalKeyMouseListener.activeMouseListener(this)
    }

    override fun deactivateListener() {
        GlobalKeyMouseListener.deactivateMouseListener(this)
    }

    override fun nativeMousePressed(nativeEvent: NativeMouseEvent) {
    }

    override fun nativeMouseClicked(nativeEvent: NativeMouseEvent) {
        actionRecorder.addMouseClick(nativeEvent.button, Point.awtPointToString(nativeEvent.point))
    }

    override fun nativeMouseReleased(nativeEvent: NativeMouseEvent) {
    }

}