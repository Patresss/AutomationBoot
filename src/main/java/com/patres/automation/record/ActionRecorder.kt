package com.patres.automation.record

import com.patres.automation.keyboard.KeyboardKey
import com.patres.automation.mapper.model.AutomationActionSerialized
import com.patres.automation.mapper.model.KeyboardFieldActionSerialized
import com.patres.automation.mapper.model.MousePointActionSerialized
import com.patres.automation.mapper.model.TextFieldActionSerialized
import com.patres.automation.type.ActionBootKeyboard
import com.patres.automation.type.ActionBootMousePoint
import com.patres.automation.type.ActionBootTextField
import javafx.beans.property.SimpleBooleanProperty
import org.slf4j.LoggerFactory

class ActionRecorder {

    companion object {
        val logger = LoggerFactory.getLogger(ActionRecorder::class.java)!!
    }

    private val listeners = listOf<RecordListener>(
            RecordKeyListener(this),
            RecordMouseListener(this),
            RecordMouseWheelListener(this)
    )
    private val recordedActions = mutableListOf<AutomationActionSerialized>()
    val recordRunningProperty = SimpleBooleanProperty(false)
    var currentTime = System.currentTimeMillis()

    fun record() {
        logger.info("Starting to record...")
        recordRunningProperty.set(true)
        listeners.forEach { it.activateListener() }
        recordedActions.clear()
        currentTime = System.currentTimeMillis()
    }

    fun stopRecording(): List<AutomationActionSerialized> {
        listeners.forEach { it.deactivateListener() }
        recordRunningProperty.set(false)
        logger.info("Stop to record...")
        return recordedActions.toList()
    }

    fun addPressKeyboardKey(key: Int) {
        val keyboardKey = KeyboardKey.findById(key)
        if (keyboardKey == null) {
            logger.warn("Unsupported key: $key - cannot be added")
        } else {
            addActionWithDelay(KeyboardFieldActionSerialized(ActionBootKeyboard.PRESS_KEYBOARD_BUTTON, listOf(keyboardKey)))
        }
    }

    fun addMouseClick(mouseButton: Int, point: String) {
        val action = when (mouseButton) {
            1 -> MousePointActionSerialized(ActionBootMousePoint.CLICK_LEFT_MOUSE_BUTTON, point)
            2 -> MousePointActionSerialized(ActionBootMousePoint.CLICK_RIGHT_MOUSE_BUTTON, point)
            3 -> MousePointActionSerialized(ActionBootMousePoint.CLICK_MIDDLE_MOUSE_BUTTON, point)
            else -> null
        }

        if (action == null) {
            logger.warn("Unsupported mouse button: $mouseButton - cannot be added")
        } else {
            addActionWithDelay(action)
        }
    }

    fun addMouseScroll(wheelRotation: Int) {
        val action = if (wheelRotation > 0) {
            TextFieldActionSerialized(ActionBootTextField.SCROLL_WHEEL_DOWN, wheelRotation.toString())
        } else {
            TextFieldActionSerialized(ActionBootTextField.SCROLL_WHEEL_UP, kotlin.math.abs(wheelRotation).toString())
        }
        addActionWithDelay(action)
    }

    private fun addActionWithDelay(action: AutomationActionSerialized) {
        addDelay()
        recordedActions.add(action)
    }

    private fun addDelay() {
        addDelay(System.currentTimeMillis() - currentTime)
        currentTime = System.currentTimeMillis()
    }

    private fun addDelay(delayMillis: Long) {
        recordedActions.add(TextFieldActionSerialized(ActionBootTextField.DELAY, delayMillis.toString()))
    }

}