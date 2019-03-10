package com.patres.automation.gui.controller

import com.jfoenix.controls.JFXSnackbar
import com.jfoenix.controls.JFXSnackbar.SnackbarEvent
import com.jfoenix.controls.JFXTabPane
import com.patres.automation.Main
import com.patres.automation.model.RootSchemaGroupModel
import com.patres.automation.serialize.RootSchemaGroupMapper
import com.patres.automation.serialize.model.RootSchemaGroupSerialized
import com.patres.automation.util.LoaderFactory
import com.patres.automation.util.LoaderFile
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.scene.control.TabPane
import javafx.scene.layout.StackPane
import kotlinx.serialization.json.Json
import java.io.File
import java.text.MessageFormat


class MainController {

    companion object {
        val FILE_IS_SAVED: String = Main.bundle.getString("message.snackbar.fileIsSaved")
        const val MESSAGE_SNACKBAR_TIMEOUT: Long = 5000
    }

    @FXML
    var root: StackPane? = null

    @FXML
    var tabPane: JFXTabPane? = null

    private lateinit var snackBar: JFXSnackbar

    private val tabContainers = ArrayList<TabContainer>()

    private val loaderFile = LoaderFile(LoaderFile.AUTOMATION_BOOT_EXTENSION, LoaderFile.AUTOMATION_BOOT_EXTENSION_TYPE)

    fun initialize() {
        snackBar = JFXSnackbar(root)
        tabPane?.tabClosingPolicy = TabPane.TabClosingPolicy.ALL_TABS
        createNewRootSchema()
    }

    @FXML
    fun createNewRootSchema() {
        val tabContainer = LoaderFactory.createRootSchemaGroup(tabPane)
        tabContainers.add(tabContainer)
    }

    @FXML
    fun openRootSchema() {
        val fileToOpen = loaderFile.chooseFileToLoad()
        if (fileToOpen != null) {
            val serializedRootGroup = fileToOpen.readText()
            val rootGroupSerialized: RootSchemaGroupSerialized = Json.parse(RootSchemaGroupSerialized.serializer(), serializedRootGroup)
            val rootGroup: RootSchemaGroupModel = RootSchemaGroupMapper.serializedToModel(rootGroupSerialized)

            val tabContainer = LoaderFactory.loadRootSchemaGroup(tabPane, rootGroup).apply {
                file = fileToOpen
            }
            tabContainers.add(tabContainer)
        }
    }


    @FXML
    fun saveExistingRootSchema() {
        getSelectedTabContainer()?.let { tabContainer ->
            var file = tabContainer.file
            if (file == null) {
                file = loaderFile.chooseFileToSave()
            }
            if (file != null) {
                saveRootSchema(file)
            }
        }
    }

    @FXML
    fun saveAsRootSchema() {
        getSelectedTabContainer()?.let { tabContainer ->
            val file = loaderFile.chooseFileToSave()
            if (file != null) {
                saveRootSchema(file)
            }
        }
    }

    private fun saveRootSchema(file: File) {
        getSelectedTabContainer()?.let { tabContainer ->
            val rootSchemaGroupSerialized = RootSchemaGroupMapper.modelToSerialize(tabContainer.rootSchema)
            val serializedRootGroup = Json.stringify(RootSchemaGroupSerialized.serializer(), rootSchemaGroupSerialized)
            file.writeText(serializedRootGroup)
            tabContainer.file = file
            setMessageToSnackBar(MessageFormat.format(FILE_IS_SAVED, file.name))
        }
    }

    private fun getSelectedTabContainer(): TabContainer? = tabContainers.find { it.tab == tabPane?.selectionModel?.selectedItem }

    private fun setMessageToSnackBar(message: String) {
        snackBar.fireEvent(
                SnackbarEvent(message, "X",
                        MESSAGE_SNACKBAR_TIMEOUT,
                        false,
                        EventHandler { snackBar.close() }))
    }

}
