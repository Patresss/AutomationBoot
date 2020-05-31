package com.patres.automation.listener.action

import com.patres.automation.action.RootSchemaGroupModel
import com.patres.automation.listener.RunStopActionListenable
import javafx.application.Platform

class RunStopLocalRootSchemaModelKeyListener(
        private val rootSchemaGroupModel: RootSchemaGroupModel
) : RunStopActionListenable {

    override fun runKeyboardKey() = rootSchemaGroupModel.actionRunner.localSettings.runActionsKeys.map { it.keyValue }

    override fun stopKeyboardKey() = rootSchemaGroupModel.actionRunner.localSettings.stopActionsKeys.map { it.keyValue }

    override fun invokeRunAction() {
        Platform.runLater {
            rootSchemaGroupModel.runAutomation()
        }
    }

    override fun invokeStopAction() {
        rootSchemaGroupModel.stopAutomation()
    }

}