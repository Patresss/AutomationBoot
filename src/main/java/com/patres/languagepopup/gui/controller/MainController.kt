package com.patres.languagepopup.gui.controller

import com.jfoenix.controls.JFXSnackbar
import com.jfoenix.controls.JFXSnackbar.SnackbarEvent
import com.jfoenix.controls.JFXTabPane
import com.patres.languagepopup.Main
import com.patres.languagepopup.gui.LoaderFactory
import com.patres.languagepopup.gui.SchemaGroup
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.control.ScrollPane
import javafx.scene.control.Tab
import javafx.scene.control.TabPane
import javafx.scene.layout.StackPane


class MainController {

    @FXML
    var root: StackPane? = null

    @FXML
    var tabPane: JFXTabPane? = null

    @FXML
    lateinit var fichesTab: StackPane

    @FXML
    lateinit var settingsTab: StackPane

    private lateinit var snackBar: JFXSnackbar

    // ================================================================================
    // Configuration methods
    // ================================================================================
    fun initialize() {
        initTabs()
        snackBar = JFXSnackbar(root)
        tabPane?.tabClosingPolicy = TabPane.TabClosingPolicy.ALL_TABS
        addNewSchema()
    }

    @FXML
    fun addNewSchema() {
        val rootSchemaGroup = LoaderFactory.loadRootSchemaGroup(tabPane)

        rootSchemaGroup.addNewSchemaGroup("1")
        rootSchemaGroup.addNewSchemaGroup("2")
        rootSchemaGroup.addNewSchemaGroup("3")

        rootSchemaGroup.schemaGroups[1].addNewSchemaGroup("2a")
        rootSchemaGroup.schemaGroups[1].addNewSchemaGroup("2b")
        rootSchemaGroup.schemaGroups[1].addNewSchemaGroup("2c")
        rootSchemaGroup.schemaGroups[1].addNewSchemaGroup("2d")



//        val innerLoader = LoaderFactory.loadSchemaGroup()
//        val innerLoader2 = LoaderFactory.loadSchemaGroup()
//        val innerLoader3 = LoaderFactory.loadSchemaGroup()
//        val innerLoader2a = LoaderFactory.loadSchemaGroup()
//        val listOfGroup = loader.getController<SchemaGroupController>().innerBox.children
//
//        val actionBlocks = ArrayList<SchemaGroupController>()
//
//
//        val group1 = innerLoader.load<StackPane>()
//        val group2 = innerLoader2.load<StackPane>()
//        val group2a = innerLoader2a.load<StackPane>()
//        val group3 = innerLoader3.load<StackPane>()
//
//        val actionBlock1 = innerLoader.getController<SchemaGroupController>()
//        val actionBlock2 = innerLoader2.getController<SchemaGroupController>()
//        val actionBlock2a = innerLoader2a.getController<SchemaGroupController>()
//        val actionBlock3 = innerLoader3.getController<SchemaGroupController>()
//
//        actionBlocks.add(actionBlock1)
//        actionBlocks.add(actionBlock2)
//        actionBlocks.add(actionBlock2a)
//        actionBlocks.add(actionBlock3)
//
//        innerLoader.getController<SchemaGroupController>().groupNameTextField.text = "1"
//        innerLoader2.getController<SchemaGroupController>().groupNameTextField.text = "2"
//        innerLoader2a.getController<SchemaGroupController>().groupNameTextField.text = "2a"
//        innerLoader3.getController<SchemaGroupController>().groupNameTextField.text = "3"
//
//        innerLoader2.getController<SchemaGroupController>().innerBox.children.add(group2a)
//
//
//
//        listOfGroup.add(group1)
//        listOfGroup.add(group2)
//        listOfGroup.add(group3)
//        actionBlock1.makeDraggable(actionBlocks)
//        actionBlock2.makeDraggable(actionBlocks)
//        actionBlock3.makeDraggable(actionBlocks)

    }

    private fun initTabs() {
        initFicheTab()
        initSettingsTab()
    }

    private fun initFicheTab() {
        val loader = FXMLLoader()
        loader.location = javaClass.getResource("/fxml/SchemaGroup.fxml")
        loader.resources = Main.bundle
        fichesTab.children.add(loader.load<StackPane>())
    }

    private fun initSettingsTab() {
        val loader = FXMLLoader()
        loader.location = javaClass.getResource("/fxml/SettingsTab.fxml")
        loader.resources = Main.bundle
        settingsTab.children.add(loader.load<StackPane>())
        loader.getController<SettingsController>().mainController = this
    }


    fun setMessageToSnackBar(message: String) {
        snackBar.fireEvent(
                SnackbarEvent(message, "X",
                        2000,
                        false,
                        EventHandler { snackBar.close() }))
    }




}
