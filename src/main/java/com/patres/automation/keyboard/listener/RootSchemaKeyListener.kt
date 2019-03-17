package com.patres.automation.keyboard.listener

import com.patres.automation.Main
import com.patres.automation.keyboard.KeyAdapter
import com.patres.automation.model.RootSchemaGroupModel
import org.jnativehook.keyboard.NativeKeyEvent
import org.jnativehook.keyboard.NativeKeyListener

class RootSchemaKeyListener(
        private val rootSchemaGroupModel: RootSchemaGroupModel
) : NativeKeyListener {

    init {
        activeListener()
    }

    var isStop = false

    private val pressedKeys = ArrayList<Int>()

    override fun nativeKeyPressed(e: NativeKeyEvent) {
        val stopKeys = rootSchemaGroupModel.localSettings.loadStopKeys()
        pressedKeys.add(KeyAdapter.getKeyEvent(e))
        if (pressedKeys.containsAll(stopKeys.map { it.keyValue })) {
            isStop = true
        }
    }

    override fun nativeKeyReleased(e: NativeKeyEvent) {
        pressedKeys.remove(KeyAdapter.getKeyEvent(e))
    }

    override fun nativeKeyTyped(e: NativeKeyEvent) {}

    private fun activeListener() {
        GlobalKeyListener.activeListener(this)
    }

    fun reset() {
        isStop = false
        pressedKeys.clear()
    }

}