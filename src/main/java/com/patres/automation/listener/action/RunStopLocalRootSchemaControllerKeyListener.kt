package com.patres.automation.listener.action

import com.patres.automation.gui.controller.model.RootSchemaGroupController
import com.patres.automation.listener.RunStopActionListenable
import javafx.application.Platform

class RunStopLocalRootSchemaControllerKeyListener(
        private val controller: RootSchemaGroupController
) : RunStopActionListenable {

    override fun runKeyboardKey() = controller.actionRunner.localSettings.runActionsKeys.map { it.keyValue }

    override fun stopKeyboardKey() = controller.actionRunner.localSettings.stopActionsKeys.map { it.keyValue }

    override fun invokeRunAction() {
        Platform.runLater {
            controller.runAutomation()
        }
    }

    override fun invokeStopAction() {
        controller.stopAutomation()
    }

}