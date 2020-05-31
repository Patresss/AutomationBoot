package com.patres.automation.gui.controller

import com.jfoenix.controls.JFXDialog
import com.patres.automation.ApplicationLauncher
import com.patres.automation.action.ActionRunner
import com.patres.automation.action.RootSchemaFiles

import com.patres.automation.action.RootSchemaGroupModel
import com.patres.automation.file.FileChooser
import com.patres.automation.file.FileType
import com.patres.automation.file.TmpFileLoader
import com.patres.automation.gui.component.snackBar.SnackBarType
import com.patres.automation.gui.component.snackBar.addMessageLanguage
import com.patres.automation.gui.controller.box.SchemaGroupController
import com.patres.automation.gui.controller.model.RootSchemaGroupController
import com.patres.automation.gui.dialog.SaveDialog
import com.patres.automation.mapper.AutomationMapper
import com.patres.automation.mapper.RootSchemaGroupMapper
import com.patres.automation.mapper.model.RootSchemaGroupSerialized
import com.patres.automation.settings.GlobalSettingsLoader
import com.patres.automation.settings.LocalSettings
import com.patres.automation.system.ApplicationInfo
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView
import javafx.beans.property.SimpleBooleanProperty
import javafx.event.Event
import javafx.event.EventHandler
import javafx.scene.control.Tab
import org.slf4j.LoggerFactory
import java.io.File

class RootSchemaLoader(val mainController: MainController) {

    companion object {
        private const val MAX_NUMBER_OF_CHARACTERS = 24
        val logger = LoggerFactory.getLogger(RootSchemaLoader::class.java)!!

        fun createRootSchemaSerializedFromFile(fileToOpen: File): RootSchemaGroupSerialized {
            val serializedRootGroup = fileToOpen.readText()
            val rootGroupSerialized: RootSchemaGroupSerialized = AutomationMapper.toObject(serializedRootGroup)
            if (rootGroupSerialized.applicationVersion != ApplicationInfo.version) {
                logger.warn("Versions differ from each other - application version ${ApplicationInfo.version} | root version: ${rootGroupSerialized.applicationVersion}")
            }
            return rootGroupSerialized
        }

    }

    private val loaderFile = FileChooser(FileType.AUTOMATION_BOOT)

    fun createNewRootSchema(): TabContainer {
        val newTmpFile = TmpFileLoader.createNewTmpFile()
        val rootSchemaFiles = RootSchemaFiles(newTmpFile)
        val actionRunner = ActionRunner(SimpleBooleanProperty(false), LocalSettings(), rootSchemaFiles)
        val tabContainer = createTabContainer(RootSchemaGroupController(actionRunner, SchemaGroupController()))
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
                val rootSchemaSerialized = createRootSchemaSerializedFromFile(fileToOpen)
                val controller = RootSchemaGroupMapper.serializedToController(rootSchemaSerialized, fileToOpen)
                createTabContainer(controller)
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
        val rootSchemaSerialized = createRootSchemaSerializedFromFile(fileToOpen)
        val controller = RootSchemaGroupMapper.serializedToController(rootSchemaSerialized, fileToOpen)
        return createTabContainer(controller)
    }

    fun openRootSchema(controller: RootSchemaGroupController): TabContainer {
        return createTabContainer(controller)
    }

    fun saveExistingRootSchema(tabContainer: TabContainer): Boolean {
        val file = if (tabContainer.rootSchemaController.actionRunner.rootFiles.isNewTmpFile()) {
            loaderFile.chooseFileToSave()
        } else {
            tabContainer.rootSchemaController.actionRunner.rootFiles.getFileToSave()
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
        tabContainer.rootSchemaController.actionRunner.rootFiles.removeTmpFile()
    }

    fun createOnCloseRequest(tabContainer: TabContainer): EventHandler<Event> {
        return EventHandler {
            if (!tabContainer.rootSchemaController.actionRunner.isSaved()) {
                if (!tabContainer.rootSchemaController.hasActions()) {
                    ApplicationLauncher.mainController?.removeTab(tabContainer)
                    tabContainer.rootSchemaController.actionRunner.rootFiles.removeTmpFile()
                } else {
                    val saveDialogPane = SaveDialog(tabContainer, mainController.rootSchemaLoader)
                    val jfxDialog = JFXDialog(ApplicationLauncher.mainPane, saveDialogPane, JFXDialog.DialogTransition.CENTER)
                    saveDialogPane.dialogKeeper = jfxDialog
                    jfxDialog.show()
                    it?.consume()
                }
            } else {
                tabContainer.rootSchemaController.stopAutomation()
                ApplicationLauncher.mainController?.removeTab(tabContainer)
            }
        }
    }

    private fun getTabName(rootSchemaController: RootSchemaGroupController): String {
        val fileName = rootSchemaController.actionRunner.getName()
        return if (fileName.length > MAX_NUMBER_OF_CHARACTERS) fileName.substring(0, MAX_NUMBER_OF_CHARACTERS) + "…" else fileName
    }

    private fun createTabContainer(rootSchemaController: RootSchemaGroupController): TabContainer {
        val fileName = getTabName(rootSchemaController)
        val newTab = Tab(fileName, rootSchemaController).apply {
            if (!rootSchemaController.actionRunner.isSaved() && rootSchemaController.hasActions()) {
                graphic = FontAwesomeIconView(FontAwesomeIcon.SAVE)
            }
        }
        val tabContainer = TabContainer(tab = newTab, rootSchemaController = rootSchemaController)
        mainController.tabPane.apply {
            mainController.addTabToContainer(tabContainer)
            tabs?.add(newTab)
            selectionModel?.select(newTab)
        }

        newTab.onCloseRequest = createOnCloseRequest(tabContainer)
        rootSchemaController.loaded = true
        return tabContainer
    }

    private fun saveFile(tabContainer: TabContainer, file: File) {
        val controller = tabContainer.rootSchemaController
        controller.actionRunner.rootFiles.saveNewFile(file)

        val rootSchemaGroupSerialized = RootSchemaGroupMapper.controllerToSerialize(controller)
        val serializedRootGroup = AutomationMapper.toJson(rootSchemaGroupSerialized)
        file.writeText(serializedRootGroup)
        tabContainer.tab.apply {
            text = getTabName(controller)
            graphic = null
        }
        GlobalSettingsLoader.save(ApplicationLauncher.globalSettings)
    }

    private fun findOpenedTab(fileToOpen: File): TabContainer? {
        val tabContainer = mainController.tabContainers
        return tabContainer
                .firstOrNull { it.rootSchemaController.actionRunner.rootFiles.isRelated(fileToOpen) }
    }

    private fun findActiveSchema(fileToOpen: File): RootSchemaGroupModel? {
        val activeActions = mainController.activeSchemasController.activeActions
        return activeActions
                .map { it.model }
                .firstOrNull { it.actionRunner.rootFiles.isRelated(fileToOpen) }
    }

}