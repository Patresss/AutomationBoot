package com.patres.automation.util

import com.patres.automation.Main
import com.patres.automation.file.FileConstants
import com.patres.automation.gui.controller.TabContainer
import com.patres.automation.model.RootSchemaGroupModel
import com.patres.automation.serialize.RootSchemaGroupMapper
import com.patres.automation.serialize.model.RootSchemaGroupSerialized
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView
import javafx.event.Event
import javafx.event.EventHandler
import javafx.scene.control.Tab
import javafx.scene.control.TabPane
import kotlinx.serialization.json.Json
import java.io.File

object RootSchemaLoader {

    private const val MAX_NUMBER_OF_CHARACTERS = 24

    fun createNewRootSchema(tabPane: TabPane): TabContainer {
        return createTabContainer(tabPane, RootSchemaGroupModel())
    }

    fun openNewRootSchema(tabPane: TabPane, fileToLoad: File): TabContainer {
        val serializedRootGroup = fileToLoad.readText()
        val rootGroupSerialized: RootSchemaGroupSerialized = Json.parse(RootSchemaGroupSerialized.serializer(), serializedRootGroup)
        val rootGroup = RootSchemaGroupMapper.serializedToModel(rootGroupSerialized)

        if (fileToLoad.extension == FileConstants.TMP_EXTENSION) {
            rootGroup.tmpFile = fileToLoad
        } else {
            rootGroup.file = fileToLoad
        }

        return createTabContainer(tabPane, rootGroup)
    }

    fun saveRootSchema(tabContainer: TabContainer, file: File) {
        val rootSchema = tabContainer.rootSchema
        val rootSchemaGroupSerialized = RootSchemaGroupMapper.modelToSerialize(rootSchema)
        val serializedRootGroup = Json.stringify(RootSchemaGroupSerialized.serializer(), rootSchemaGroupSerialized)
        file.writeText(serializedRootGroup)
        rootSchema.file = file
        tabContainer.tab.apply {
            text = getTabName(rootSchema)
            graphic = null
        }
        rootSchema.saved = true
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
        newTab.onCloseRequest = EventHandler<Event> { Main.mainController?.tabContainers?.remove(tabContainer) }
        return tabContainer
    }

    private fun getTabName(model: RootSchemaGroupModel): String {
        val fileName = model.file?.nameWithoutExtension ?: model.tmpFile.nameWithoutExtension
        return if (fileName.length > MAX_NUMBER_OF_CHARACTERS) fileName.substring(0, MAX_NUMBER_OF_CHARACTERS) + "…" else fileName
    }

}