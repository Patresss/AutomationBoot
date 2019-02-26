package com.patres.automation.gui.controller

import com.jfoenix.controls.JFXSnackbar
import com.jfoenix.controls.JFXSnackbar.SnackbarEvent
import com.jfoenix.controls.JFXTabPane
import com.patres.automation.model.RootSchemaGroupModel
import com.patres.automation.serialize.RootSchemaGroupMapper
import com.patres.automation.serialize.model.RootSchemaGroupSerialized
import com.patres.automation.util.LoaderFactory
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.scene.control.Tab
import javafx.scene.control.TabPane
import javafx.scene.layout.StackPane
import kotlinx.serialization.json.Json
import java.io.File


class MainController {


    @FXML
    var root: StackPane? = null

    @FXML
    var tabPane: JFXTabPane? = null

    private lateinit var snackBar: JFXSnackbar


    private val tabRootSchemaMap = HashMap<Tab?, RootSchemaGroupModel>()

    fun initialize() {
        snackBar = JFXSnackbar(root)
        tabPane?.tabClosingPolicy = TabPane.TabClosingPolicy.ALL_TABS
        addNewSchema()
    }

    @FXML
    fun addNewSchema() {
        val pairTabRoot = LoaderFactory.createRootSchemaGroup(tabPane)
        tabRootSchemaMap.put(pairTabRoot.first, pairTabRoot.second)
    }

    @FXML
    fun saveRootSchema() {
        getSelectedRootSchema()?.let { rootSchema ->
            val rootSchemaGroupSerialized = RootSchemaGroupMapper.modelToSerialize(rootSchema)
            val serializedRootGroup = Json.stringify(RootSchemaGroupSerialized.serializer(), rootSchemaGroupSerialized)
            val homeDirectory = "P:\\Programowanie\\Projekty\\Mouse and keyboard automat\\Examples"
            File(homeDirectory, "file.json").writeText(serializedRootGroup)
        }

    }

    @FXML
    fun loadRootSchema() {
        val homeDirectory = "P:\\Programowanie\\Projekty\\Mouse and keyboard automat\\Examples"
        val serializedRootGroup = File(homeDirectory, "file.json").readText()
        val rootGroupSerialized: RootSchemaGroupSerialized = Json.parse(RootSchemaGroupSerialized.serializer(), serializedRootGroup)
        val rootGroup: RootSchemaGroupModel = RootSchemaGroupMapper.serializedToModel(rootGroupSerialized).apply {
            controller.insidePane.content = schemaGroup.controller.getMainNode()
        }
        val tab = tabPane?.selectionModel?.selectedItem
        tabRootSchemaMap[tab] = rootGroup
        tab?.content = rootGroup.controller.rootStackPane
    }

    fun getSelectedRootSchema(): RootSchemaGroupModel? = tabRootSchemaMap[tabPane?.selectionModel?.selectedItem]

    fun setMessageToSnackBar(message: String) {
        snackBar.fireEvent(
                SnackbarEvent(message, "X",
                        2000,
                        false,
                        EventHandler { snackBar.close() }))
    }

}
