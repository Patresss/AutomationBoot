package com.patres.automation.keyboard.listener

import com.patres.automation.action.RootSchemaGroupModel
import com.patres.automation.keyboard.KeyAdapter
import javafx.application.Platform
import org.jnativehook.keyboard.NativeKeyEvent
import org.jnativehook.keyboard.NativeKeyListener

class RootSchemaKeyListener(
        private val rootSchemaGroupModel: RootSchemaGroupModel
) : NativeKeyListener {

    init {
        activeListener()
    }

    private val pressedKeys = HashSet<Int>()

    override fun nativeKeyPressed(keyEvent: NativeKeyEvent) {
        println("Key prss: " + KeyAdapter.getKeyEvent(keyEvent))

        pressedKeys.add(KeyAdapter.getKeyEvent(keyEvent))
        checkStopKeys()
        checkRunKeys()
    }

    private fun checkStopKeys() {
        val stopKeys = rootSchemaGroupModel.localSettings.loadStopKeys().map { it.keyValue }
        if (stopKeys.isNotEmpty() && pressedKeys.containsAll(stopKeys)) {
            rootSchemaGroupModel.stopAutomation()
        }
    }

    private fun checkRunKeys() {
        val runKeysSetting = rootSchemaGroupModel.localSettings.runKeysSetting.map { it.keyValue }
        if (runKeysSetting.isNotEmpty() && pressedKeys.containsAll(runKeysSetting)) {
            Platform.runLater {
                rootSchemaGroupModel.runAutomation(false)
            }
        }
    }

    override fun nativeKeyReleased(e: NativeKeyEvent) {
        pressedKeys.remove(KeyAdapter.getKeyEvent(e))
    }

    override fun nativeKeyTyped(e: NativeKeyEvent) {}

    private fun activeListener() {
        GlobalKeyMouseListener.activeKeyListener(this)
    }

    fun reset() {
        pressedKeys.clear()
    }

}