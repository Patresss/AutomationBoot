package com.patres.automation.util

import com.jfoenix.controls.JFXDialog
import com.patres.automation.file.FileType
import com.patres.automation.Main
import com.patres.automation.file.FileChooser
import com.patres.automation.file.FileConstants
import com.patres.automation.gui.controller.TabContainer
import com.patres.automation.gui.dialog.SaveDialog
import com.patres.automation.model.RootSchemaGroupModel
import com.patres.automation.serialize.AutomationMapper
import com.patres.automation.serialize.RootSchemaGroupMapper
import com.patres.automation.serialize.model.RootSchemaGroupSerialized
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
        val fileToOpen = loaderFile.chooseFileToLoad()
        if (fileToOpen != null) {
            val serializedRootGroup = fileToOpen.readText()
            val rootGroupSerialized: RootSchemaGroupSerialized = AutomationMapper.toObject(serializedRootGroup)
            val rootGroup = RootSchemaGroupMapper.serializedToModel(rootGroupSerialized)

            if (fileToOpen.extension == FileConstants.TMP_EXTENSION) {
                rootGroup.tmpFile = fileToOpen
            } else {
                rootGroup.file = fileToOpen
            }
            return createTabContainer(tabPane, rootGroup)
        }
        return null
    }

    // TODO refactor duplicate ^
    fun openRootSchema(tabPane: TabPane, fileToOpen: File): TabContainer {
        val serializedRootGroup = fileToOpen.readText()
        val rootGroupSerialized: RootSchemaGroupSerialized = AutomationMapper.toObject(serializedRootGroup)
        val rootGroup = RootSchemaGroupMapper.serializedToModel(rootGroupSerialized)

        if (fileToOpen.extension == FileConstants.TMP_EXTENSION) {
            rootGroup.tmpFile = fileToOpen
        } else {
            rootGroup.file = fileToOpen
        }
        return createTabContainer(tabPane, rootGroup)
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
        val rootSchemaGroupSerialized = RootSchemaGroupMapper.modelToSerialize(rootSchema)
        val serializedRootGroup = AutomationMapper.toJson(rootSchemaGroupSerialized)
        file.writeText(serializedRootGroup)
        rootSchema.file = file
        tabContainer.tab.apply {
            text = getTabName(rootSchema)
            graphic = null
        }
        GlobalSettingsLoader.save(Main.globalSettings)
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
                val jfxDialog = JFXDialog(Main.mainPane, saveDialogPane, JFXDialog.DialogTransition.CENTER)
                saveDialogPane.dialogKeeper = jfxDialog
                jfxDialog.show()
                it?.consume()
            } else {
                Main.mainController?.removeTab(tabContainer)
            }
        }
    }

    private fun getTabName(model: RootSchemaGroupModel): String {
        val fileName = model.getName()
        return if (fileName.length > MAX_NUMBER_OF_CHARACTERS) fileName.substring(0, MAX_NUMBER_OF_CHARACTERS) + "…" else fileName
    }

}