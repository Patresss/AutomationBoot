package com.patres.automation.listener

import com.patres.automation.gui.controller.MainController
import com.patres.automation.keyboard.KeyAdapter
import com.patres.automation.listener.action.RunStopLocalRootSchemaKeyListener
import org.jnativehook.keyboard.NativeKeyEvent
import org.jnativehook.keyboard.NativeKeyListener

class RunStopKeyListener(private val mainController: MainController) : NativeKeyListener {

    init {
        activeListener()
    }

    private val pressedKeys = HashSet<Int>()

    override fun nativeKeyPressed(keyEvent: NativeKeyEvent) {
        pressedKeys.add(KeyAdapter.getKeyEvent(keyEvent))
        checkStopKeys()
        checkRunKeys()
    }

    private fun checkRunKeys() {
        calculateActiveSchema().forEach { action ->
            if (action.runKeyboardKey().isNotEmpty() && pressedKeys.containsAll(action.runKeyboardKey())) {
                action.invokeRunAction()
                reset()
            }
        }
    }

    private fun checkStopKeys() {
        calculateActiveSchema().forEach { action ->
            if (action.stopKeyboardKey().isNotEmpty() && pressedKeys.containsAll(action.stopKeyboardKey())) {
                action.invokeStopAction()
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

    private fun reset() {
        pressedKeys.clear()
    }

    private fun calculateActiveSchema(): List<RunStopLocalRootSchemaKeyListener> {
        val map = mainController.findAllowedAction().map { RunStopLocalRootSchemaKeyListener(it) }
        println("Number of listners: ${map.size} | $map")
        return map
    }

}