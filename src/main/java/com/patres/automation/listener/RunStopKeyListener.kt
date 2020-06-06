package com.patres.automation.listener

import com.patres.automation.gui.controller.MainController
import com.patres.automation.keyboard.KeyAdapter
import com.patres.automation.listener.action.RunStopGlobalRootSchemaKeyListener
import com.patres.automation.listener.action.RunStopLocalRootSchemaControllerKeyListener
import com.patres.automation.listener.action.RunStopLocalRootSchemaModelKeyListener
import com.patres.automation.listener.action.RunStopRecordKeyListener
import org.jnativehook.keyboard.NativeKeyEvent
import org.jnativehook.keyboard.NativeKeyListener

class RunStopKeyListener(private val mainController: MainController) : NativeKeyListener {

    private val constListeners = setOf(
            RunStopGlobalRootSchemaKeyListener(mainController),
            RunStopRecordKeyListener(mainController)
    )

    private val pressedKeys = HashSet<Int>()

    override fun nativeKeyPressed(keyEvent: NativeKeyEvent) {
        pressedKeys.add(KeyAdapter.getKeyEvent(keyEvent))
        checkStopKeys()
        checkRunKeys()
    }

    private fun checkRunKeys() {
        calculateAllListeners().forEach { action ->
            if (action.runKeyboardKey().isNotEmpty() && pressedKeys.containsAll(action.runKeyboardKey())) {
                action.invokeRunAction()
                reset()
            }
        }
    }

    private fun checkStopKeys() {
        calculateAllListeners().forEach { action ->
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

    fun activeListener() {
        GlobalKeyMouseListener.activeKeyListener(this)
    }

    private fun reset() {
        pressedKeys.clear()
    }

    private fun calculateActiveSchemaListeners() =
            mainController.activeSchemasController.activeActions.map { RunStopLocalRootSchemaModelKeyListener(it.model) } +
            mainController.openedRootSchemas.map { RunStopLocalRootSchemaControllerKeyListener(it) }

    private fun calculateAllListeners() = constListeners + calculateActiveSchemaListeners()

}