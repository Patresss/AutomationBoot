package com.patres.automation.listener

import com.patres.automation.keyboard.KeyAdapter
import org.jnativehook.keyboard.NativeKeyEvent
import org.jnativehook.keyboard.NativeKeyListener

class RunStopKeyListener(private val actionListenable: MutableSet<RunStopActionListenable> = HashSet()) : NativeKeyListener {

    init {
        activeListener()
    }

    private val pressedKeys = HashSet<Int>()

    override fun nativeKeyPressed(keyEvent: NativeKeyEvent) {
        pressedKeys.add(KeyAdapter.getKeyEvent(keyEvent))
        checkStopKeys()
        checkRunKeys()
    }

    private fun checkStopKeys() {
        actionListenable.forEach { action ->
            if (action.stopKeyboardKey().isNotEmpty() && pressedKeys.containsAll(action.stopKeyboardKey())) {
                action.invokeStopAction()
                reset()
            }
        }
    }

    private fun checkRunKeys() {
        actionListenable.forEach { action ->
            if (action.runKeyboardKey().isNotEmpty() && pressedKeys.containsAll(action.runKeyboardKey())) {
                action.invokeRunAction()
                reset()
            }
        }
    }

    override fun nativeKeyReleased(keyEvent: NativeKeyEvent) {
        pressedKeys.remove(KeyAdapter.getKeyEvent(keyEvent))
    }

    override fun nativeKeyTyped(keyEvent: NativeKeyEvent) {
    }

    private fun activeListener() {
        GlobalKeyMouseListener.activeKeyListener(this)
    }

    fun reset() {
        pressedKeys.clear()
    }

    fun addListeners(vararg listeners: RunStopActionListenable ) {
        actionListenable.addAll(listeners)
    }

    fun removeListeners(vararg listeners: RunStopActionListenable ) {
        actionListenable.removeAll(listeners)
    }

}