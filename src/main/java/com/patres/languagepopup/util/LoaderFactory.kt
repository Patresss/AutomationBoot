package com.patres.languagepopup.util

import com.patres.languagepopup.Main
import com.patres.languagepopup.gui.controller.model.RootSchemaGroupController
import com.patres.languagepopup.gui.controller.model.SchemaGroupController
import com.patres.languagepopup.gui.controller.model.TextActionController
import com.patres.languagepopup.model.RootSchemaGroupModel
import com.patres.languagepopup.model.SchemaGroupModel
import javafx.fxml.FXMLLoader
import javafx.scene.control.Tab
import javafx.scene.control.TabPane

object LoaderFactory {

    fun createTextActionController(root: RootSchemaGroupModel, parent: SchemaGroupModel): TextActionController {
        val loader = FXMLLoader()
        loader.location = javaClass.getResource("/fxml/TextAction.fxml")
        loader.resources = Main.bundle
        loader.load<TextActionController>()
        return loader.getController()
    }

    fun createSchemaGroupController(root: RootSchemaGroupModel, parent: SchemaGroupModel?): SchemaGroupController {
        val loader = FXMLLoader()
        loader.location = javaClass.getResource("/fxml/SchemaGroup.fxml")
        loader.resources = Main.bundle
        loader.load<SchemaGroupController>()
        val controller = loader.getController<SchemaGroupController>()
        return controller
    }

    fun createRootSchemaGroupController(): RootSchemaGroupController {
        val loader = FXMLLoader()
        loader.location = javaClass.getResource("/fxml/RootSchemaGroup.fxml")
        loader.resources = Main.bundle
        loader.load<RootSchemaGroupController>()
        return loader.getController()
    }

    fun createRootSchemaGroup(tabPane: TabPane?): RootSchemaGroupModel {
        val rootSchemaGroup = RootSchemaGroupModel().apply {
            controller.insidePane.content = schemaGroup.controller.getMainOutsideNode()
        }

        val newTab = Tab("New Tab", rootSchemaGroup.controller.rootStackPane)
        tabPane?.apply {
            tabs?.add(newTab)
            selectionModel?.select(newTab)
        }

        return rootSchemaGroup
    }
}
