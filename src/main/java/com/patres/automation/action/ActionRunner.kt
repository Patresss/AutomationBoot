package com.patres.automation.action

import com.patres.automation.ApplicationLauncher
import com.patres.automation.file.TmpFileLoader
import com.patres.automation.gui.controller.model.RootSchemaGroupController
import com.patres.automation.gui.dialog.LogManager
import com.patres.automation.gui.dialog.SaveRecordedActionsDialog
import com.patres.automation.listener.record.ActionRecorder
import com.patres.automation.settings.LocalSettings
import javafx.application.Platform
import javafx.beans.property.BooleanProperty
import javafx.beans.property.SimpleBooleanProperty
import javafx.concurrent.Task
import org.slf4j.LoggerFactory

class ActionRunner(val automationRunningProperty: BooleanProperty) {

    companion object {
        private val logger = LoggerFactory.getLogger(ActionRunner::class.java)
    }

    fun runAutomation(schemaGroupModel: SchemaGroupModel) {
        logger.info("Run automation")
        try {
            val runTask = createRunTask(schemaGroupModel)
            Thread(runTask).start()
        } catch (e: Exception) {
            LogManager.showAndLogException(e)
        }
    }

    fun stopAutomation() {
        logger.info("Stop automation")
        automationRunningProperty.set(false)
    }

    private fun createRunTask(schemaGroupModel: SchemaGroupModel): Task<Void> {
        return object : Task<Void>() {
            override fun call(): Void? {
                automationRunningProperty.set(true)
                val applicationStageIsActive = ApplicationLauncher.mainStage.isIconified
                try {
                    Platform.runLater { ApplicationLauncher.mainStage.isIconified = true }
                    logger.info("Running root actions...")
                    schemaGroupModel.runAction()
                    logger.info("Completed root actions")
                } catch (e: Throwable) {
                    LogManager.showAndLogException(e)
                } finally {
                    Platform.runLater { ApplicationLauncher.mainStage.isIconified = applicationStageIsActive }
                    automationRunningProperty.set(false)
                }
                return null
            }
        }
    }

}