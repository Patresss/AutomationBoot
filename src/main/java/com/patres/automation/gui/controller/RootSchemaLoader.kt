package com.patres.automation.gui.controller

import com.jfoenix.controls.JFXDialog
import com.patres.automation.ApplicationLauncher
import com.patres.automation.action.RootSchemaFiles

import com.patres.automation.action.RootSchemaGroupModel
import com.patres.automation.file.FileChooser
import com.patres.automation.file.FileType
import com.patres.automation.file.TmpFileLoader
import com.patres.automation.gui.component.snackBar.SnackBarType
import com.patres.automation.gui.component.snackBar.addMessageLanguage
import com.patres.automation.gui.dialog.SaveDialog
import com.patres.automation.mapper.AutomationMapper
import com.patres.automation.mapper.RootSchemaGroupMapper
import com.patres.automation.mapper.model.RootSchemaGroupSerialized
import com.patres.automation.settings.GlobalSettingsLoader
import com.patres.automation.system.ApplicationInfo
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView
import javafx.application.Application
import javafx.event.Event
import javafx.event.EventHandler
import javafx.scene.control.Tab
import org.slf4j.LoggerFactory
import java.io.File

class RootSchemaLoader(val mainController: MainController) {

    companion object {
        private const val MAX_NUMBER_OF_CHARACTERS = 24
        val logger = LoggerFactory.getLogger(RootSchemaLoader::class.java)!!

        fun createRootSchemaGroupFromFile(fileToOpen: File): RootSchemaGroupModel {
            val serializedRootGroup = fileToOpen.readText()
            val rootGroupSerialized: RootSchemaGroupSerialized = AutomationMapper.toObject(serializedRootGroup)
            if (rootGroupSerialized.applicationVersion != ApplicationInfo.version) {
                logger.warn("Versions differ from each other - application version ${ApplicationInfo.version} | root version: ${rootGroupSerialized.applicationVersion}")
            }
            return RootSchemaGroupMapper.serializedToModel(rootGroupSerialized, fileToOpen)
        }

    }

    private val loaderFile = FileChooser(FileType.AUTOMATION_BOOT)

    fun createNewRootSchema(): TabContainer {
        val newTmpFile = TmpFileLoader.createNewTmpFile()
        val rootSchemaFiles = RootSchemaFiles(newTmpFile)
        val tabContainer = createTabContainer(RootSchemaGroupModel(rootFiles = rootSchemaFiles))
        saveFile(tabContainer, newTmpFile)
        return tabContainer
    }

    fun openRootSchema() {
        val fileToOpen = loaderFile.chooseToLoad()
        if (fileToOpen != null) {
            // Open existing tab
            findOpenedTab(fileToOpen)?.also {
                logger.debug("Open existing tab")
                mainController.tabPane.selectionModel.select(it.tab)
                mainController.snackBar.addMessageLanguage(SnackBarType.INFO, "message.snackbar.schemaIsAlreadyOpen")
            } ?:
            // Find Active if doesn't exist
            findActiveSchema(fileToOpen)?.also {
                logger.debug("Open existing active schema")
                mainController.activeSchemasController.removeActiveSchemaFromList(it)
                createTabContainer(it)
                mainController.snackBar.addMessageLanguage(SnackBarType.INFO, "message.snackbar.schemaWasInTheActiveSchemas")
            } ?:
            // Open if doesn't exist
            run {
                logger.debug("Open schema")
                openRootSchema(fileToOpen)
            }
        }
    }

    fun openRootSchema(fileToOpen: File): TabContainer {
        return createTabContainer(createRootSchemaGroupFromFile(fileToOpen))
    }

    fun openRootSchema(rootGroup: RootSchemaGroupModel): TabContainer {
        return createTabContainer(rootGroup)
    }

    fun saveExistingRootSchema(tabContainer: TabContainer): Boolean {
        val file = if (tabContainer.rootSchema.rootFiles.isNewTmpFile()) {
            loaderFile.chooseFileToSave()
        } else {
            tabContainer.rootSchema.rootFiles.getFileToSave()
        }
        if (file != null) {
            saveFile(tabContainer, file)
            return true
        }
        return false
    }

    fun saveAsRootSchema(tabContainer: TabContainer): Boolean {
        val file = loaderFile.chooseFileToSave()
        if (file != null) {
            saveFile(tabContainer, file)
            return true
        }
        return false
    }

    fun removeTmpFile(tabContainer: TabContainer) {
        tabContainer.rootSchema.rootFiles.removeTmpFile()
    }

    fun createOnCloseRequest(tabContainer: TabContainer): EventHandler<Event> {
        return EventHandler {
            if (!tabContainer.rootSchema.isSaved()) {
                val saveDialogPane = SaveDialog(tabContainer, mainController.rootSchemaLoader)
                val jfxDialog = JFXDialog(ApplicationLauncher.mainPane, saveDialogPane, JFXDialog.DialogTransition.CENTER)
                saveDialogPane.dialogKeeper = jfxDialog
                jfxDialog.show()
                it?.consume()
            } else {
                tabContainer.rootSchema.stopAutomation()
                ApplicationLauncher.mainController.removeTab(tabContainer)
            }
        }
    }

    private fun getTabName(model: RootSchemaGroupModel): String {
        val fileName = model.getName()
        return if (fileName.length > MAX_NUMBER_OF_CHARACTERS) fileName.substring(0, MAX_NUMBER_OF_CHARACTERS) + "…" else fileName
    }

    private fun createTabContainer(rootGroup: RootSchemaGroupModel): TabContainer {
        val fileName = getTabName(rootGroup)
        val newTab = Tab(fileName, rootGroup.controller).apply {
            if (!rootGroup.isSaved()) {
                graphic = FontAwesomeIconView(FontAwesomeIcon.SAVE)
            }
        }
        val tabContainer = TabContainer(tab = newTab, rootSchema = rootGroup)
        mainController.tabPane.apply {
            mainController.addTabToContainer(tabContainer)
            tabs?.add(newTab)
            selectionModel?.select(newTab)
        }

        newTab.onCloseRequest = createOnCloseRequest(tabContainer)
        rootGroup.loaded = true
        return tabContainer
    }

    private fun saveFile(tabContainer: TabContainer, file: File) {
        val rootSchema = tabContainer.rootSchema
        val rootSchemaGroupSerialized = RootSchemaGroupMapper.modelToSerialize(rootSchema, null)
        val serializedRootGroup = AutomationMapper.toJson(rootSchemaGroupSerialized)
        file.writeText(serializedRootGroup)
        rootSchema.rootFiles.saveNewFile(file)
        tabContainer.tab.apply {
            text = getTabName(rootSchema)
            graphic = null
        }
        GlobalSettingsLoader.save(ApplicationLauncher.globalSettings)
    }

    private fun findOpenedTab(fileToOpen: File): TabContainer? {
        val tabContainer = mainController.tabContainers
        return tabContainer
                .firstOrNull { it.rootSchema.rootFiles.isRelated(fileToOpen) }
    }

    private fun findActiveSchema(fileToOpen: File): RootSchemaGroupModel? {
        val activeActions = mainController.activeSchemasController.activeActions
        return activeActions
                .firstOrNull { it.rootFiles.isRelated(fileToOpen) }
    }

}