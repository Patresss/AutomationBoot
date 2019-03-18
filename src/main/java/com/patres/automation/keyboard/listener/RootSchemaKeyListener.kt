package com.patres.automation.keyboard.listener

import com.patres.automation.Main
import com.patres.automation.keyboard.KeyAdapter
import com.patres.automation.model.RootSchemaGroupModel
import javafx.application.Platform
import org.jnativehook.keyboard.NativeKeyEvent
import org.jnativehook.keyboard.NativeKeyListener
import kotlin.concurrent.thread

class RootSchemaKeyListener(
        private val rootSchemaGroupModel: RootSchemaGroupModel
) : NativeKeyListener {

    init {
        activeListener()
    }

    var isStop = false

    private val pressedKeys = ArrayList<Int>()

    override fun nativeKeyPressed(keyEvent: NativeKeyEvent) {
        checkStopKeys(keyEvent)
        checkRunKeys(keyEvent)
    }

    private fun checkStopKeys(keyEvent: NativeKeyEvent) {
        pressedKeys.add(KeyAdapter.getKeyEvent(keyEvent))

        val stopKeys = rootSchemaGroupModel.localSettings.loadStopKeys().map { it.keyValue }
        if (pressedKeys.containsAll(stopKeys)) {
            isStop = true
        }
    }

    private fun checkRunKeys(keyEvent: NativeKeyEvent) {
        pressedKeys.add(KeyAdapter.getKeyEvent(keyEvent))

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
        GlobalKeyListener.activeListener(this)
    }

    fun reset() {
        isStop = false
        pressedKeys.clear()
    }

}