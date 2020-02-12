package com.patres.automation.listener.action

import com.patres.automation.ApplicationLauncher
import com.patres.automation.gui.controller.MainController
import com.patres.automation.listener.RunStopActionListenable

class RunStopRecordKeyListener(
        private val mainController: MainController
) : RunStopActionListenable {

    override fun runKeyboardKey() = ApplicationLauncher.globalSettings.startRecordKeys.map { it.keyValue }

    override fun stopKeyboardKey() = ApplicationLauncher.globalSettings.stopRecordKeys.map { it.keyValue }

    override fun invokeRunAction() {
        mainController.getSelectedTabContainer()?.rootSchema?.startRecord()
    }

    override fun invokeStopAction() {
        mainController.getSelectedTabContainer()?.rootSchema?.stopRecord()
    }

}