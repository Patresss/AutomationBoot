package com.patres.automation.gui.controller.saveBackScreen.activeSchema

import com.patres.automation.ApplicationLauncher
import com.patres.automation.action.RootSchemaGroupModel
import com.patres.automation.gui.animation.SliderAnimation
import com.patres.automation.gui.controller.MainController
import com.patres.automation.gui.controller.saveBackScreen.SaveBackScreenController
import com.patres.automation.mapper.AutomationMapper
import com.patres.automation.mapper.model.RootSchemaGroupSerialized
import com.patres.automation.util.RootSchemaLoader
import com.patres.automation.util.fromBundle
import org.slf4j.LoggerFactory
import java.io.File


class ActiveSchemasController(
        private val mainController: MainController
) : SaveBackScreenController("menu.activeSchemas.details") {

    companion object {
        private val logger = LoggerFactory.getLogger(ActiveSchemasController::class.java)
    }

    val toEditSchema: MutableList<ActiveSchemaItemController> = mutableListOf()
    val activeActions: MutableList<RootSchemaGroupModel> = mutableListOf()

    init {
        initChangeDetectors()
        calculateRootSchemaModels()
    }

    override fun saveChanges() {
        openSchemasToEdit()
        ApplicationLauncher.globalSettings.editAndSave {
            activeSchemas = getActiveSchemaFromUi()
        }
        activeActions.removeIf { !ApplicationLauncher.globalSettings.activeSchemas.contains(it.getFilePathToSettings()) }
        logger.debug("Saved ${activeActions.size} schema models as active")
        setMessageToSnackBar(fromBundle("message.snackbar.settingsSave"))
    }

    private fun openSchemasToEdit() {
        toEditSchema
                .map { File(it.path) }
                .filter { it.exists() }
                .forEach { mainController.loadModelFromFile(it) }
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
        val activeSchemas: List<ActiveSchemaItemController> = ApplicationLauncher.globalSettings.calculateActiveSchemasAsFiles()
                .sortedBy { it.nameWithoutExtension }
                .map { mapFileActiveSchemaItem(it) }
        mainVBox.children.addAll(activeSchemas)
    }

    private fun mapFileActiveSchemaItem(file: File): ActiveSchemaItemController {
        return ActiveSchemaItemController(this, file.nameWithoutExtension, file.path)
    }

    fun removeActiveSchemaFromList(action: ActiveSchemaItemController) {
        mainVBox.children.remove(action)
    }

    private fun getActiveSchemaFromUi(): MutableList<String> {
        return mainVBox.children
                .filterIsInstance<ActiveSchemaItemController>()
                .map { it.path }
                .toMutableList()
    }

    private fun calculateRootSchemaModels() {
        activeActions.clear()
        val rootSchemaModels = ApplicationLauncher.globalSettings.calculateActiveSchemasAsFiles()
                .mapNotNull { mapFileToRootSchema(it) }
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

}
