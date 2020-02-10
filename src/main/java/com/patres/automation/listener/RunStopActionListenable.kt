package com.patres.automation.listener

interface RunStopActionListenable {
    fun runKeyboardKey(): List<Int>
    fun stopKeyboardKey(): List<Int>

    fun invokeRunAction()
    fun invokeStopAction()
}