package com.patres.automation.listener.action

import com.patres.automation.ApplicationLauncher
import com.patres.automation.action.RootSchemaGroupModel
import com.patres.automation.gui.controller.MainController
import com.patres.automation.listener.RunStopActionListenable

class RunStopGlobalRootSchemaKeyListener(
        private val mainController: MainController
) : RunStopActionListenable {

    override fun runKeyboardKey() = emptyList<Int>()

    override fun stopKeyboardKey() = ApplicationLauncher.globalSettings.stopActionKeys.map { it.keyValue }

    override fun invokeRunAction() {
    }

    override fun invokeStopAction() {
        mainController.rootSchemas.forEach { it.stopAutomation() }
    }

}