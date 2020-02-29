package com.patres.automation.gui.controller.saveBackScreen.activeSchema

import com.patres.automation.ApplicationLauncher
import com.patres.automation.gui.animation.SliderAnimation
import com.patres.automation.gui.controller.MainController
import com.patres.automation.gui.controller.saveBackScreen.SaveBackScreenController
import com.patres.automation.gui.dialog.LogManager
import com.patres.automation.mapper.AutomationMapper
import com.patres.automation.mapper.model.RootSchemaGroupSerialized
import com.patres.automation.util.fromBundle
import javafx.fxml.FXML
import java.io.File


class ActiveSchemasController(
        private val mainController: MainController
) : SaveBackScreenController("menu.activeSchemas.details") {

    val toEditSchema: MutableList<ActiveSchemaItemController> = mutableListOf()

    init {
        initChangeDetectors()
    }

    @FXML
    override fun save() {
        try {
            saveButton.isDisable = true
            openSchemasToEdit()
            ApplicationLauncher.globalSettings.activeSchemas = getActiveSchemaFromUi()

            setMessageToSnackBar(fromBundle("message.snackbar.settingsSave"))
        } catch (e: Exception) {
            LogManager.showAndLogException(e)
        }
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

    override fun reloadSettingsValue() {
        mainVBox.children.clear()
        toEditSchema.clear()

        val activeSchemas: List<ActiveSchemaItemController> = ApplicationLauncher.globalSettings.calculateActiveSchemasAsFiles()
                .sortedBy { it.nameWithoutExtension }
                .map { mapFileActiveSchemaItem(it) }
        mainVBox.children.addAll(activeSchemas)
    }

    private fun mapFileActiveSchemaItem(file: File): ActiveSchemaItemController {
        val rootSchemaGroupSerialized = AutomationMapper.toObject<RootSchemaGroupSerialized>(file.readText())
        val fileWithName = if(rootSchemaGroupSerialized.file != null) { // to avoid tmp name
            File(rootSchemaGroupSerialized.file)
        } else {
            file
        }
        return ActiveSchemaItemController(this, fileWithName.nameWithoutExtension, file.path)
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

}
