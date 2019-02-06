package com.patres.languagepopup.util

import com.patres.languagepopup.Main
import com.patres.languagepopup.model.RootSchemaGroup
import com.patres.languagepopup.gui.controller.RootSchemaGroupController
import com.patres.languagepopup.gui.controller.SchemaGroupController
import javafx.fxml.FXMLLoader
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

    fun loadRootSchemaGroup(): FXMLLoader {
        val loader = FXMLLoader()
        loader.location = javaClass.getResource("/fxml/RootSchemaGroup.fxml")
        loader.resources = Main.bundle
        loader.load<RootSchemaGroupController>()
        return loader
    }

    fun createRootSchemaGroup(tabPane: TabPane?): RootSchemaGroup {
        val rootSchemaGroup = RootSchemaGroup().apply {
            controller.insidePane.content = schemaGroup.controller.getMainOutsideNode()
        }

        val newTab = Tab("New Tab", rootSchemaGroup.controller.rootStackPane)
        tabPane?.apply {
            tabs?.add(newTab)
            selectionModel?.select(newTab)
        }

        val controller = rootSchemaGroup.schemaGroup.controller
        controller.mainSchemaBox.children.remove(controller.selectActionButton)

        return rootSchemaGroup
    }
}
