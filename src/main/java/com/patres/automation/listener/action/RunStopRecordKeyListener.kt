package com.patres.automation.listener.action

import com.patres.automation.ApplicationLauncher
import com.patres.automation.action.RootSchemaGroupModel
import com.patres.automation.listener.RunStopActionListenable
import javafx.application.Platform

class RunStopRecordKeyListener(
        private val rootSchemaGroupModel: RootSchemaGroupModel
) : RunStopActionListenable {

    override fun runKeyboardKey() = ApplicationLauncher.globalSettings.startRecordKeys.map { it.keyValue }

    override fun stopKeyboardKey() = ApplicationLauncher.globalSettings.stopRecordKeys.map { it.keyValue }

    override fun invokeRunAction() {
        Platform.runLater {
            rootSchemaGroupModel.startRecord()
        }
    }

    override fun invokeStopAction() {
        Platform.runLater {
            ApplicationLauncher.mainStage.isIconified = false
            rootSchemaGroupModel.stopRecord()
        }
    }

}