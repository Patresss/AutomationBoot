package com.patres.automation.util

import com.jfoenix.controls.JFXDialog
import com.patres.automation.ApplicationLauncher

import com.patres.automation.action.RootSchemaGroupModel
import com.patres.automation.file.FileChooser
import com.patres.automation.file.FileType
import com.patres.automation.gui.controller.TabContainer
import com.patres.automation.gui.dialog.SaveDialog
import com.patres.automation.mapper.AutomationMapper
import com.patres.automation.mapper.RootSchemaGroupMapper
import com.patres.automation.mapper.model.RootSchemaGroupSerialized
import com.patres.automation.settings.GlobalSettingsLoader
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView
import javafx.event.Event
import javafx.event.EventHandler
import javafx.scene.control.Tab
import javafx.scene.control.TabPane
import java.io.File

object RootSchemaLoader {

    private const val MAX_NUMBER_OF_CHARACTERS = 24
    private val loaderFile = FileChooser(FileType.AUTOMATION_BOOT)

    fun createNewRootSchema(tabPane: TabPane): TabContainer {
        return createTabContainer(tabPane, RootSchemaGroupModel()).apply {
            rootSchema.saveTmpFile()
        }
    }

    fun openRootSchema(tabPane: TabPane): TabContainer? {
        val fileToOpen = loaderFile.chooseToLoad()
        if (fileToOpen != null) {
            return openRootSchema(tabPane, fileToOpen)
        }
        return null
    }

    fun openRootSchema(tabPane: TabPane, fileToOpen: File): TabContainer {
        return createTabContainer(tabPane, createRootSchemaGroupFromFile(fileToOpen))
    }

    fun createRootSchemaGroupFromFile(fileToOpen: File): RootSchemaGroupModel {
        val serializedRootGroup = fileToOpen.readText()
        val rootGroupSerialized: RootSchemaGroupSerialized = AutomationMapper.toObject(serializedRootGroup)
        val rootGroup = RootSchemaGroupMapper.serializedToModel(rootGroupSerialized)

        if (fileToOpen.extension == FileType.TEMP_AUTOMATION_BOOT.extension) {
            rootGroup.tmpFile = fileToOpen
        } else {
            rootGroup.file = fileToOpen
        }
        return rootGroup
    }

    fun saveExistingRootSchema(tabContainer: TabContainer): Boolean {
        var file = tabContainer.rootSchema.file
        if (file == null) {
            file = loaderFile.chooseFileToSave()
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

    private fun saveFile(tabContainer: TabContainer, file: File) {
        val rootSchema = tabContainer.rootSchema.apply {
            saved = true
        }
        val rootSchemaGroupSerialized = RootSchemaGroupMapper.modelToSerializeWithNewFileName(rootSchema, file)
        val serializedRootGroup = AutomationMapper.toJson(rootSchemaGroupSerialized)
        file.writeText(serializedRootGroup)
        rootSchema.file = file
        tabContainer.tab.apply {
            text = getTabName(rootSchema)
            graphic = null
        }
        GlobalSettingsLoader.save(ApplicationLauncher.globalSettings)
    }

    fun removeTmpFile(tabContainer: TabContainer) {
        tabContainer.rootSchema.tmpFile.delete()
        tabContainer.rootSchema.saved = true
    }

    private fun createTabContainer(tabPane: TabPane, rootGroup: RootSchemaGroupModel): TabContainer {
        val fileName = getTabName(rootGroup)
        val newTab = Tab(fileName, rootGroup.controller).apply {
            if (!rootGroup.saved) {
                graphic = FontAwesomeIconView(FontAwesomeIcon.SAVE)
            }
        }
        val tabContainer = TabContainer(tab = newTab, rootSchema = rootGroup)
        tabPane.apply {
            tabs?.add(newTab)
            selectionModel?.select(newTab)
        }

        newTab.onCloseRequest = createOnCloseRequest(tabContainer)
        rootGroup.loaded = true
        return tabContainer
    }

    fun createOnCloseRequest(tabContainer: TabContainer): EventHandler<Event> {
        return EventHandler {
            if (!tabContainer.rootSchema.saved) {
                val saveDialogPane = SaveDialog(tabContainer)
                val jfxDialog = JFXDialog(ApplicationLauncher.mainPane, saveDialogPane, JFXDialog.DialogTransition.CENTER)
                saveDialogPane.dialogKeeper = jfxDialog
                jfxDialog.show()
                it?.consume()
            } else {
                ApplicationLauncher.mainController.removeTab(tabContainer)
            }
        }
    }

    private fun getTabName(model: RootSchemaGroupModel): String {
        val fileName = model.getName()
        return if (fileName.length > MAX_NUMBER_OF_CHARACTERS) fileName.substring(0, MAX_NUMBER_OF_CHARACTERS) + "…" else fileName
    }

}