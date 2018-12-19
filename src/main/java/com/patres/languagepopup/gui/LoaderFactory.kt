package com.patres.languagepopup.gui

import com.patres.languagepopup.Main
import com.patres.languagepopup.gui.controller.SchemaGroupController
import javafx.fxml.FXMLLoader
import javafx.scene.control.ScrollPane
import javafx.scene.control.Tab
import javafx.scene.control.TabPane

object LoaderFactory {

    fun loadSchemaGroup(): FXMLLoader {
        val loader = FXMLLoader()
        loader.location = javaClass.getResource("/fxml/SchemaGroup.fxml")
        loader.resources = Main.bundle
        loader.load<SchemaGroupController>()
        return loader
    }

    fun loadRootSchemaGroup(tabPane: TabPane?):RootSchemaGroup {
        val rootSchemaGroup = RootSchemaGroup()
        val scrollPane = ScrollPane().apply {
            content = rootSchemaGroup.schemaGroup.controller.getMainOutsideNode()
            isFitToWidth = true
        }
        val newTab = Tab("New Tab", scrollPane)
        tabPane?.apply {
            tabs?.add(newTab)
            selectionModel?.select(newTab)
        }
        val controller = rootSchemaGroup.schemaGroup.controller
        controller.outsideBox.children.remove(controller.buttonsGridPane)
        controller.outsideBox.style = "-fx-border-insets: 20 10 20 10;"

        return rootSchemaGroup
    }
}
