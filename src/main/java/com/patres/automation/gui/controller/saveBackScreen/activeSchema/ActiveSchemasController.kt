package com.patres.automation.gui.controller.saveBackScreen.activeSchema

import com.patres.automation.ApplicationLauncher
import com.patres.automation.action.RootSchemaGroupModel
import com.patres.automation.gui.animation.SliderAnimation
import com.patres.automation.gui.component.snackBar.SnackBarType
import com.patres.automation.gui.component.snackBar.addMessageLanguageWhenIsLoaded
import com.patres.automation.gui.controller.MainController
import com.patres.automation.gui.controller.RootSchemaLoader
import com.patres.automation.gui.controller.saveBackScreen.SaveBackScreenController
import com.patres.automation.mapper.RootSchemaGroupMapper
import com.patres.automation.mapper.model.RootSchemaGroupSerialized
import org.slf4j.LoggerFactory
import java.io.File


class ActiveSchemasController(
        private val mainController: MainController
) : SaveBackScreenController("menu.activeSchemas.details") {

    companion object {
        private val logger = LoggerFactory.getLogger(ActiveSchemasController::class.java)
    }

    private val rootSchemaLoader = RootSchemaLoader(mainController)
    val toEditSchema: MutableList<ActiveSchemaItemController> = mutableListOf()
    val toRemoveSchema: MutableList<ActiveSchemaItemController> = mutableListOf()

    val activeActions: MutableList<ActiveSchemaItemController> = mutableListOf()

    init {
        calculateRootSchemaModels()
    }

    override fun saveChanges() {
        openSchemasToEdit()
        stopSchemasToClose()
        ApplicationLauncher.globalSettings.editAndSave {
            activeSchemas = getActiveSchemaFromUi()
        }
        activeActions.removeIf {
            val filePath = it.model.actionRunner.rootFiles.getFilePathToSettings()
            !ApplicationLauncher.globalSettings.activeSchemas.contains(filePath)
        }

        logger.debug("Saved ${activeActions.size} schema models as active")
        snackBar.addMessageLanguageWhenIsLoaded(isLoaded, SnackBarType.INFO, "message.snackbar.settingsSave")
    }

    private fun openSchemasToEdit() {
        toEditSchema
                .map { RootSchemaGroupMapper.serializedToController(it.serialized, it.model.actionRunner.rootFiles.currentFile) }
                .forEach {
                    rootSchemaLoader.openRootSchema(it)
                }
    }

    private fun stopSchemasToClose() {
        toRemoveSchema
                .map { it.model }
                .forEach { it.stopAutomation() }

    }

    override fun initChangeDetectors() {
        ApplicationLauncher.globalSettings.activeSchemas
    }

    override fun backToPreviousWindow() {
        SliderAnimation.backToTheWindow(mainController.currentTabPane(), this, mainController.centerStackPane)
    }

    override fun reloadSettings() {
        mainVBox.children.clear()
        toEditSchema.clear()
        mainVBox.children.addAll(activeActions)
    }

    fun removeActiveSchemaFromUiList(action: ActiveSchemaItemController) {
        mainVBox.children.remove(action)
    }

    private fun getActiveSchemaFromUi(): MutableList<String> {
        return mainVBox.children
                .filterIsInstance<ActiveSchemaItemController>()
                .map { it.model.actionRunner.rootFiles.currentFile.path }
                .toMutableList()
    }

    private fun calculateRootSchemaModels() {
        activeActions.clear()
        val rootSchemaModels = ApplicationLauncher.globalSettings.calculateActiveSchemasAsFiles().mapNotNull {
            mapFileToActiveSchema(it)
        }
        activeActions.addAll(rootSchemaModels)
    }

    private fun mapFileToActiveSchema(file: File): ActiveSchemaItemController? {
        return try {
            val serialized = RootSchemaLoader.createRootSchemaSerializedFromFile(file)
            val model = RootSchemaGroupMapper.serializedToModel(serialized, file)
            ActiveSchemaItemController(this, model, serialized)
        } catch (e: Exception) {
            logger.error("Cannot create RootSchemaModel from file ${file.absoluteFile}", e)
            null
        }
    }

    fun addNewSchemaModel(model: RootSchemaGroupModel, serialized: RootSchemaGroupSerialized) {
        ApplicationLauncher.globalSettings.editAndSave {
            activeSchemas.add(model.actionRunner.rootFiles.getFilePathToSettings())
        }
        activeActions.add(ActiveSchemaItemController(this, model, serialized))
    }

    fun removeActiveSchemaFromList(rootSchema: RootSchemaGroupModel) {
        activeActions.removeIf { it.model == rootSchema }
        ApplicationLauncher.globalSettings.editAndSave {
            activeSchemas.removeIf { it == rootSchema.actionRunner.rootFiles.currentFile.absolutePath }
        }
    }

}
