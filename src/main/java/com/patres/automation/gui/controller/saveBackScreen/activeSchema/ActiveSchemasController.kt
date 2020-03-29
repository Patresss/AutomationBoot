package com.patres.automation.gui.controller.saveBackScreen.activeSchema

import com.patres.automation.ApplicationLauncher
import com.patres.automation.action.RootSchemaGroupModel
import com.patres.automation.gui.animation.SliderAnimation
import com.patres.automation.gui.component.snackBar.SnackBarType
import com.patres.automation.gui.component.snackBar.addMessageLanguageWhenIsLoaded
import com.patres.automation.gui.controller.MainController
import com.patres.automation.gui.controller.saveBackScreen.SaveBackScreenController
import com.patres.automation.util.RootSchemaLoader
import org.slf4j.LoggerFactory
import java.io.File


class ActiveSchemasController(
        private val mainController: MainController
) : SaveBackScreenController("menu.activeSchemas.details") {

    companion object {
        private val logger = LoggerFactory.getLogger(ActiveSchemasController::class.java)
    }

    private val rootSchemaLoader = RootSchemaLoader(mainController)
    val toEditSchema: MutableList<RootSchemaGroupModel> = mutableListOf()
    val toRemoveSchema: MutableList<RootSchemaGroupModel> = mutableListOf()

    val activeActions: MutableList<RootSchemaGroupModel> = mutableListOf()

    init {
        initChangeDetectors()
        calculateRootSchemaModels()
    }

    override fun saveChanges() {
        openSchemasToEdit()
        stopSchemasToClose()
        ApplicationLauncher.globalSettings.editAndSave {
            activeSchemas = getActiveSchemaFromUi()
        }
        activeActions.removeIf { !ApplicationLauncher.globalSettings.activeSchemas.contains(it.getFilePathToSettings()) }

        logger.debug("Saved ${activeActions.size} schema models as active")
        snackBar.addMessageLanguageWhenIsLoaded(isLoaded, SnackBarType.INFO, "message.snackbar.settingsSave")
    }

    private fun openSchemasToEdit() {
        toEditSchema.forEach { rootSchemaLoader.openRootSchema(it) }
    }

    private fun stopSchemasToClose() {
        toRemoveSchema.forEach { it.stopAutomation() }

    }

    override fun initChangeDetectors() {
        ApplicationLauncher.globalSettings.activeSchemas
    }

    override fun backToPreviousWindow() {
        SliderAnimation.backToTheWindow(mainController.tabPane, this, mainController.centerStackPane)
    }

    override fun reloadSettings() {
        mainVBox.children.clear()
        toEditSchema.clear()
//        calculateRootSchemaModels()
        val activeActionsItems = activeActions.map { mapRootToActiveSchemaItem(it) }
        mainVBox.children.addAll(activeActionsItems)
    }

    private fun mapRootToActiveSchemaItem(rootSchemaGroupModel: RootSchemaGroupModel): ActiveSchemaItemController {
        return ActiveSchemaItemController(this, rootSchemaGroupModel)
    }

    fun removeActiveSchemaFromUiList(action: ActiveSchemaItemController) {
        mainVBox.children.remove(action)
    }

    private fun getActiveSchemaFromUi(): MutableList<String> {
        return mainVBox.children
                .filterIsInstance<ActiveSchemaItemController>()
                .map { it.rootSchemaGroupModel.rootFiles.currentFile.path }
                .toMutableList()
    }

    private fun calculateRootSchemaModels() {
        activeActions.clear()
        val rootSchemaModels = ApplicationLauncher.globalSettings.calculateActiveSchemasAsFiles().mapNotNull { mapFileToRootSchema(it) }
        activeActions.addAll(rootSchemaModels)
    }

    private fun mapFileToRootSchema(file: File): RootSchemaGroupModel? {
        return try {
            RootSchemaLoader.createRootSchemaGroupFromFile(file)
        } catch (e: Exception) {
            logger.error("Cannot create RootSchemaModel from file ${file.absoluteFile}", e)
            null
        }
    }

    fun addNewSchemaModel(rootSchema: RootSchemaGroupModel) {
        ApplicationLauncher.globalSettings.editAndSave {
            activeSchemas.add(rootSchema.getFilePathToSettings())
        }
        activeActions.add(rootSchema)
    }

    fun removeActiveSchemaFromList(rootSchema: RootSchemaGroupModel) {
        activeActions.remove(rootSchema)
        ApplicationLauncher.globalSettings.editAndSave {
            activeSchemas.removeIf { it == rootSchema.rootFiles.currentFile.absolutePath }
        }
    }

}
